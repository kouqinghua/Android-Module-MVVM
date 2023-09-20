package com.module.wifi.activity

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.base.mvvm.v.BaseActivity
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.common.router.RoutePath
import com.module.wifi.R
import com.module.wifi.databinding.ActivityWifiBinding

@Route(path = RoutePath.Wifi.ACTIVITY_WIFI)
class WifiActivity : BaseActivity<ActivityWifiBinding, BaseViewModel>() {


    override val mViewModel by viewModels<BaseViewModel>()

    override fun ActivityWifiBinding.initView() {

    }

    override fun initialize() {

    }

    override fun initObserve() {

    }

    override fun initRequestData() {

    }
}