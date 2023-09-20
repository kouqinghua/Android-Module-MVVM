package com.lib.common.router.service.wifi

import android.app.Activity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.common.router.RoutePath
import com.lib.common.router.service.BaseServiceWrap

class WifiServiceWrap private constructor() : BaseServiceWrap() {

    @Autowired(name = RoutePath.Wifi.SERVICE_WIFI)
    lateinit var service: IWifiService

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            WifiServiceWrap()
        }
    }

    fun toWifiActivity(activity: Activity) {
        ARouter.getInstance().build(RoutePath.Wifi.ACTIVITY_WIFI).navigation(activity)
    }

}