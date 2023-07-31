package com.func.box.base

import com.func.box.api.Api
import com.lib.common.base.SdkViewModel
import com.lib.common.net.RetrofitClient

open class BaseViewModel : SdkViewModel() {

    private val mRetrofit by lazy {
        RetrofitClient.getRetrofit(Api.BASE_URL)
    }

    protected val mApi by lazy {
        mRetrofit.create(Api::class.java)
    }
}