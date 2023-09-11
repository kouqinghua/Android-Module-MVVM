package com.module.login.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.base.mvvm.v.BaseActivity
import com.lib.base.utils.console
import com.lib.common.router.RoutePath
import com.module.login.databinding.LoginLayoutBinding
import com.module.login.viewmodel.LoginViewModel
import org.koin.android.ext.android.get

@Route(path = RoutePath.Login.ACTIVITY_LOGIN)
class LoginActivity : BaseActivity<LoginLayoutBinding, LoginViewModel>() {
    override val mViewModel: LoginViewModel = get()

    override fun initialize() {

    }

    override fun LoginLayoutBinding.initView() {
    }

    override fun initObserve() {
        mViewModel.data.observe(this){
            console(it[0].title)
        }
    }

    override fun initRequestData() {
        mViewModel.getBanner()
    }
}