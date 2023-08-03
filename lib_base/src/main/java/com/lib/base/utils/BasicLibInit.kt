package com.lib.base.utils

import android.annotation.SuppressLint
import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.base.BuildConfig
import com.lib.base.app.BaseApplication
import com.lib.base.network.log.LogCat
import com.lib.base.network.state.NetworkStateClient

object BasicLibInit {

    private val IS_DEBUG : Boolean = BuildConfig.DEBUG
    private val application : Application = BaseApplication.application
    /**
     * 阿里路由 ARouter 初始化
     */
    fun initARouter(): String {
        // 测试环境下打开ARouter的日志和调试模式 正式环境需要关闭
        if (IS_DEBUG) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application)
        return "ARouter -->> init complete"
    }

    /**
     * 腾讯 MMKV 初始化
     */
    fun initMMKV(): String {
        MMKVUtils.init(application)
        return "MMKV -->> init complete"
    }

    /**
     * 初始化网络状态监听客户端
     * @return Unit
     */
    @SuppressLint("MissingPermission")
    fun initNetworkStateClient(): String {
        NetworkStateClient.register(application)
        return "NetworkStateClient -->> init complete"
    }

    fun initLogcat() : String {
        LogCat.setDebug(IS_DEBUG)
        return "LogCat -->> init complete"
    }
}