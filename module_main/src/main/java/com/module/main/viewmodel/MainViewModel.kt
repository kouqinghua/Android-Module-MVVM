package com.module.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.base.network.initiateRequest
import com.module.main.bean.MainBanner
import com.module.main.repository.MainRepository


class MainViewModel(private val repo: MainRepository) : BaseViewModel() {

    fun getRooms(): List<String> {
        return listOf("清风阁", "红莲居", "百合居", "玉宇阁", "凌寒厢", "思轩窗", "星轸台", "如意厅", "福禄厅", "天喜苑", "祥瑞苑", "七星门", "财神殿", "迎春坊")
    }

    fun getBanner(): MutableLiveData<MainBanner> {
        val data = MutableLiveData<MainBanner>()
        initiateRequest(loadState) {
            data.value = repo.getBanner()
        }

        return data
    }
}