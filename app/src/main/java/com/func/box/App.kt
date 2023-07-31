package com.func.box

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.func.box.di.module
import com.ktx.lib.base.BaseConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    //ARouter调试开关
    private var isDebugARouter = true

    override fun onCreate() {
        super.onCreate()

        //初始化标题栏
        BaseConfig.apply {

        }

        startKoin {
            androidContext(this@App)
            modules(module)
        }

        if (isDebugARouter) {
            //下面两行必须写在init之前，否则这些配置在init中将无效
            ARouter.openLog()
            //开启调试模式（如果在InstantRun模式下运行，必须开启调试模式！
            // 线上版本需要关闭，否则有安全风险）
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}