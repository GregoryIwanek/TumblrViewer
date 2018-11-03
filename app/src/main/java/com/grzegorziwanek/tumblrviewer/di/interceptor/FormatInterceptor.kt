package com.grzegorziwanek.tumblrviewer.di.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
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
        val builder = response.newBuilder()

        val responseBody = formatToJsonRequest(request, response)
        return if (responseBody != null) builder.body(responseBody).build()
        else builder.build()
    }

    private fun formatToJsonRequest(request: Request, response: Response): ResponseBody? {
        val bodyStr = bodyToString(response.body())
        return if (shouldFormatToJson(bodyStr)) getJsonBody(request, bodyStr) else null
    }

    private fun shouldFormatToJson(bodyStr: String): Boolean =
        if (hasContent(bodyStr)) !isJsonFormat(bodyStr)
        else false

    private fun hasContent(str: String): Boolean =
        str.isNotBlank()

    private fun isJsonFormat(bodyStr: String): Boolean {
        var str = String(bodyStr.toCharArray())
        str = cutRedundant(str, false)
        return str.isBlank()
    }

    private fun getJsonBody(request: Request, bodyStr: String): ResponseBody =
        ResponseBody.create(request.body()?.contentType(),
            cutRedundant(bodyStr,true))

    private fun cutRedundant(content: String, cutOuterSide: Boolean): String {
        val copy = String(content.toByteArray())
        val indexFirst = copy.indexOfFirst { it == CHAR_BRACKET_OPEN }
        val indexLast = copy.indexOfLast { it == CHAR_BRACKET_CLOSE }
        return if (cutOuterSide) copy.substring(indexFirst, indexLast + 1)
        else copy.removeRange(indexFirst, indexLast)
    }

    private fun bodyToString(body: ResponseBody?): String =
        try {
            val buffer = Buffer()
            body?.source()?.readAll(buffer)
            buffer.readUtf8()
        } catch (exception: IOException) {
            "did not work"
        }

    companion object {
        private const val CHAR_BRACKET_OPEN = '{'
        private const val CHAR_BRACKET_CLOSE = '}'
    }
}