package com.module.login.activity

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.base.mvvm.v.BaseActivity
import com.lib.base.utils.console
import com.lib.common.router.RoutePath
import com.module.login.R
import com.module.login.databinding.ActivityLoginBinding
import com.module.login.viewmodel.LoginViewModel
import org.koin.android.ext.android.get


@Route(path = RoutePath.Login.ACTIVITY_LOGIN)
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {


    override val mViewModel :LoginViewModel = get()

    override fun initialize() {
    }

    override fun ActivityLoginBinding.initView() {
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