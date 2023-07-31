package com.func.box.api

import com.func.box.bean.BannerBean
import com.lib.common.net.BaseResp
import retrofit2.http.POST

interface Api {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @POST("/banner/json")
    suspend fun getBanner(): BaseResp<BannerBean>

}