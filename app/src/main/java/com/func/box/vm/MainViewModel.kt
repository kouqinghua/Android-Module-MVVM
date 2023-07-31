package com.func.box.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.func.box.base.BaseViewModel
import com.func.box.bean.BannerBean
import com.lib.common.base.LoadState
import kotlinx.coroutines.launch


class MainViewModel : BaseViewModel() {

    val banners by lazy {
        MutableLiveData<BannerBean>()
    }

    fun getRooms(): List<String> {
        changeState(LoadState.SUCCESS)
        return listOf("清风阁", "红莲居", "百合居", "玉宇阁", "凌寒厢", "思轩窗", "星轸台", "如意厅", "福禄厅", "天喜苑", "祥瑞苑", "七星门", "财神殿", "迎春坊")
    }

    fun getBanner() {
        mLoadState.postValue(LoadState.LOADING)
        viewModelScope.launch {
            val banner = mApi.getBanner()
            val data = banner.getData()
            banners.postValue(data)
            mLoadState.postValue(LoadState.HIDE)
        }
    }
}