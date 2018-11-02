package com.grzegorziwanek.tumblrviewer.di.interceptor

import okhttp3.*
import okio.Buffer
import java.io.IOException
import javax.inject.Inject

/**
 * Consists of interceptor which purpose is to make sure http call response
 * passed further is in JSON format.
 *
 * (02.11.2018)
 * For reference check: https://derek.tumblr.com/api/read/json
 * Tumblr API v1 contains misleading description about returned format of calls to read data.
 * According to description, format of returned data is JSON, but it's a JSON
 * wrapped inside javascript value, ex. "var tumblr_api_read = { JSON_response_content };"
 *
 * In order to serialise response to objects, there is a need to transform response to JSON.
 *
 * FormatInterceptor removes redundant outsides of JSON enclosure {}.
 */
class FormatInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val builder = request.newBuilder()

        if (shouldFormatToJson(response)) builder.put(getJSONBody(request, response))

        return chain.proceed(builder.build())
    }

    private fun shouldFormatToJson(response: Response?): Boolean =
        if (hasContent(response)) !isJsonFormat(response!!)
        else false

    private fun hasContent(response: Response?): Boolean =
        try {
            !response?.body()?.source()?.exhausted()!!
        } catch (exception: IOException) {
            false
        }

    private fun isJsonFormat(response: Response): Boolean {
        val str = bodyToString(response.body()!!)
        cutRedundant(str, false)
        return str.isBlank()
    }

    private fun getJSONBody(request: Request, response: Response): RequestBody =
        RequestBody.create(request.body()!!.contentType(),
            cutRedundant(bodyToString(response.body()!!), true))

    private fun cutRedundant(content: String, cutOuterSide: Boolean): String {
        val indexFirst = content.indexOfFirst { it.equals("{") }
        val indexLast = content.indexOfLast { it.equals("}") }
        return if (cutOuterSide) content.substring(indexFirst, indexLast)
        else content.removeRange(indexFirst, indexLast)
    }

    private fun bodyToString(body: ResponseBody): String =
        try {
            val buffer = Buffer()
            body.source().readAll(buffer)
            buffer.readUtf8()
        } catch (exception: IOException) {
            "did not work"
        }
}