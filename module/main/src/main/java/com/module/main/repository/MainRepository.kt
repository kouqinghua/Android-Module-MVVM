package com.module.main.repository

import androidx.lifecycle.MutableLiveData
import com.lib.base.mvvm.m.BaseRepository
import com.lib.base.network.RetrofitFactory
import com.lib.base.network.UIState
import com.lib.base.network.dataConvert
import com.lib.common.api.BaseApi
import com.module.main.api.MainApi
import com.module.main.bean.MainBanner

class MainRepository(private val loadState: MutableLiveData<UIState>) : BaseRepository() {

    private val api by lazy {
        RetrofitFactory.instance.createService(MainApi::class.java, BaseApi.BASE_URL)
    }

    suspend fun getBanner(): MainBanner {
        return api.getBanner().dataConvert(loadState)
    }
}