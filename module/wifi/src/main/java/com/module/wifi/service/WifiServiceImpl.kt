package com.module.wifi.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.common.router.RoutePath
import com.lib.common.router.service.wifi.IWifiService

@Route(path = RoutePath.Wifi.SERVICE_WIFI)
class WifiServiceImpl : IWifiService {

    override fun init(context: Context?) {

    }
}