package com.lib.base.network

import android.util.SparseArray
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory private constructor() {

    companion object {
        private const val CONNECT_TIMEOUT = 60L
        private const val READ_TIMEOUT = 60L
        private const val WRITE_TIMEOUT = 10L
        private val urls: SparseArray<String> = SparseArray()
        private val retrofits: SparseArray<Retrofit> = SparseArray()

        val instance by lazy {
            RetrofitFactory()
        }
    }

    /**
     * @param baseUrl 地址
     * @param clazz api
     */
    fun <Service> createService(clazz: Class<Service>, baseUrl: String): Service {
        val indexOfValue = urls.indexOfValue(baseUrl)
        val retrofit =
            if (indexOfValue >= 0) {
                //内存中存在直接取出来使用
                retrofits.get(indexOfValue)
            } else {
                //内存中没有则重新获取
                getRetrofit(baseUrl, newClient())
            }
        return retrofit.create(clazz)
    }

    private fun getRetrofit(baseUrl: String, newClient: OkHttpClient): Retrofit =
        Retrofit.Builder().run {
            baseUrl(baseUrl)
            client(newClient)
            addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            val build = build()
            val index = urls.size()
            urls.append(index, baseUrl)
            retrofits.append(index, build)
            build
        }

    private fun newClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            followRedirects(false)
            cookieJar(LocalCookieJar())
            addInterceptor(LogInterceptor())
//            addInterceptor(initCommonInterceptor())
        }.build()

//    private fun initCommonInterceptor(): Interceptor {
//        return Interceptor { chain ->
//            val request = chain.request()
//                .newBuilder()
//                .addHeader("Content-Type", "application/json")
//                .addHeader("charset", "UTF-8")
//                .addHeader("token", getToken())
//                .addHeader("platform", "2")
//                .addHeader("deviceId", DeviceID.getDeviceId())
//                .addHeader("versionCode", "" + AppInfoUtils.getVersionCode(BaseApplication.context))
//                .addHeader("versionName", AppInfoUtils.getVersionName(BaseApplication.context))
//                .build()
//            val response = chain.proceed(request)
//            response
//        }
//    }
}