package com.grzegorziwanek.tumblrviewer.di.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

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