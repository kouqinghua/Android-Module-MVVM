package com.lib.base.utils

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.base.BuildConfig
import com.lib.base.app.BaseApplication

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
}