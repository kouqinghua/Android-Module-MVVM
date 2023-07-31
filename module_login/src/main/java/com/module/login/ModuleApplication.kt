package com.module.login

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter

class ModuleApplication : Application() {

    //ARouter调试开关
    private var isDebugARouter = true

    override fun onCreate() {
        super.onCreate()

        if (isDebugARouter) {
            //下面两行必须写在init之前，否则这些配置在init中将无效
            ARouter.openLog()
            //开启调试模式（如果在InstantRun模式下运行，必须开启调试模式！
            // 线上版本需要关闭，否则有安全风险）
            ARouter.openDebug()
        }
        ARouter.init(this)

        Log.e("login组件---->", "login组件的Application启动了")
    }
}