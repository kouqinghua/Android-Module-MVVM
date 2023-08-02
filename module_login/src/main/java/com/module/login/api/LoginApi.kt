package com.module.login.api

import com.lib.base.network.BaseResponse
import com.module.login.bean.LoginBanner
import retrofit2.http.POST

interface LoginApi {

    companion object {
        const val BANNER_URL = "/banner/json"
    }

    @POST(BANNER_URL)
    suspend fun getBanner(): BaseResponse<LoginBanner>
}