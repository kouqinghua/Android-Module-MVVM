package com.module.main.api

import com.lib.base.network.BaseResponse
import com.module.main.bean.MainBanner
import retrofit2.http.POST

interface MainApi {

    companion object {
        const val BANNER_URL = "/banner/json"
    }

    @POST(BANNER_URL)
    suspend fun getBanner(): BaseResponse<MainBanner>
}