package com.module.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.base.network.initiateRequest
import com.module.login.bean.LoginBanner
import com.module.login.repository.LoginRepository

class LoginViewModel(private val repo: LoginRepository) : BaseViewModel() {

    //    private val repo by lazy { LoginRepository(loadState) }
    val data = MutableLiveData<LoginBanner>()
    fun getBanner() {
        initiateRequest(loadState) {
            data.value = repo.getBanner()
        }
    }
}