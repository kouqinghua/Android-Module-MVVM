package com.module.main.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.base.mvvm.v.BaseActivity
import com.lib.common.router.RoutePath
import com.lib.common.router.service.login.LoginServiceWrap
import com.lib.common.router.service.progress.ProgressServiceWrap
import com.module.main.databinding.ActivityMainBinding
import com.module.main.viewmodel.MainViewModel
import org.koin.android.ext.android.get

@Route(path = RoutePath.Main.ACTIVITY_MAIN)
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val mViewModel: MainViewModel = get()

    override fun initialize() {
        hideBackIcon()
        setBarTitle("首页功能列表")
    }

    override fun ActivityMainBinding.initView() {
        toLogin.setOnClickListener {
            LoginServiceWrap.instance.toLoginActivity(mmActivity)
        }

        toProgress.setOnClickListener {
            ProgressServiceWrap.instance.toProgressActivity(mmActivity)
        }
    }

    override fun initObserve() {

    }

    override fun initRequestData() {

    }

}