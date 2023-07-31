package com.ktx.lib.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response = chain.proceed(original)
        Log.d("___request___", String.format("...\n请求链接：%s", original.url()));
        Log.d("___response___", String.format("...\n请求响应%s", getResponseInfo(response)));
        return response
    }
}

private fun getResponseInfo(response: Response): String {
    var str = ""
    if (!response.isSuccessful) {
        return str
    }
    val responseBody = response.body()
    val contentLength = responseBody?.contentLength()
    val source = responseBody?.source()
    try {
        source?.request(Long.MAX_VALUE)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    val buffer = source?.buffer()
    val charset = Charset.forName("utf-8")
    if (contentLength != 0L) {
        str = buffer?.clone()?.readString(charset) ?: ""
    }
    return str
}