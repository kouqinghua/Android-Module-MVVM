package com.ktx.lib.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        Log.d("request_response", response.body().toString())
        return response
    }
}