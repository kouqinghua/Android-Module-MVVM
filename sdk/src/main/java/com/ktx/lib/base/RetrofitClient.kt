package com.ktx.lib.base

import com.ktx.lib.interceptor.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val mOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .callTimeout(30, TimeUnit.SECONDS)
        .build()

    fun getRetrofit(base: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
    }
}