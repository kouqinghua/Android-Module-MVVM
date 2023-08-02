package com.module.login.repository

import androidx.lifecycle.MutableLiveData
import com.lib.base.mvvm.m.BaseRepository
import com.lib.base.network.RetrofitFactory
import com.lib.base.network.UIState
import com.lib.base.network.dataConvert
import com.lib.common.api.BaseApi
import com.module.login.api.LoginApi
import com.module.login.bean.LoginBanner

class LoginRepository(private val loadState: MutableLiveData<UIState>) : BaseRepository() {

    private val api by lazy {
        RetrofitFactory.instance.createService(LoginApi::class.java, BaseApi.BASE_URL)
    }

    suspend fun getBanner(): LoginBanner {
        return api.getBanner().dataConvert(loadState)
    }
}