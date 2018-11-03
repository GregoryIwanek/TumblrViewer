package com.grzegorziwanek.tumblrviewer.di.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * Consists of interceptor which removes extra "localhost" string from request.
 *
 * Example Tumblr api v1 url: "http://elektranatchios.tumblr.com/"
 * Part of path which should be set with annotation @Path lies directly after "https://"
 *
 * Retrofit has to have base url set.
 * Retrofit.baseUrl(...) by default can't be just "https://"
 *
 * As a result, base url of retrofit was set to "https://localhost" where "localhost" has to be removed.
 */
class UrlInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        if (containsLocalhostInBaseUrl(request)) removeLocalhost(request, builder)
        return chain.proceed(builder.build())
    }

    private fun containsLocalhostInBaseUrl(request: Request): Boolean =
        (request.url().toString().contains(LOCALHOST))

    private fun removeLocalhost(request: Request, builder: Request.Builder) {
        val str = request.url().url().toString()
        val newStr = str.replace(LOCALHOST, "", true)
        builder.url(newStr)
    }

    companion object {
        private const val LOCALHOST = "localhost/"
    }
}