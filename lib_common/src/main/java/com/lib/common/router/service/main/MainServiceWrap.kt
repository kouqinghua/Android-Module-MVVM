package com.lib.common.router.service.main

import android.app.Activity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.common.router.RoutePath
import com.lib.common.router.service.BaseServiceWrap

class MainServiceWrap private constructor() : BaseServiceWrap() {

    @Autowired(name = RoutePath.Main.SERVICE_MAIN)
    lateinit var service: IMainService

    companion object {
        val instance by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MainServiceWrap()
        }
    }


    fun toMainTabActivity(activity: Activity) {
        ARouter.getInstance().build(RoutePath.Main.ACTIVITY_MAIN).navigation(activity)
    }
}