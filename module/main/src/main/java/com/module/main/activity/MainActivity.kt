package com.module.main.activity

import android.graphics.Color
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.lib.base.app.BaseBindAdapter
import com.lib.base.mvvm.v.BaseActivity
import com.lib.base.utils.console
import com.lib.base.utils.toast
import com.lib.common.router.RoutePath
import com.lib.common.router.service.camera.CameraServiceWrap
import com.lib.common.router.service.grid.GridServiceWrap
import com.lib.common.router.service.login.LoginServiceWrap
import com.lib.common.router.service.progress.ProgressServiceWrap
import com.lib.common.router.service.wifi.WifiServiceWrap
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

        toCamera.setOnClickListener {
            CameraServiceWrap.instance.toCameraActivity(mmActivity)
        }

        toMeal.setOnClickListener {
            GridServiceWrap.instance.toGridActivity(mmActivity)
        }

        toWifi.setOnClickListener {
            WifiServiceWrap.instance.toWifiActivity(mmActivity)
        }

        toProgress.setOnClickListener {
            ProgressServiceWrap.instance.toProgressActivity(mmActivity)
        }

        toFingerprint.setOnClickListener {
            toast("功能暂未开放")
//            WifiServiceWrap.instance.toWifiActivity(mmActivity)
        }
    }

    override fun initObserve() {

    }

    override fun initRequestData() {

    }

}