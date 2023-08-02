package com.lib.common.router.service.login

import android.app.Activity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.common.router.RoutePath
import com.lib.common.router.service.BaseServiceWrap

class LoginServiceWrap private constructor() : BaseServiceWrap() {

    @Autowired(name = RoutePath.Login.SERVICE_LOGIN)
    lateinit var service: ILoginService

    companion object {
        val instance by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LoginServiceWrap()
        }
    }


    fun toLoginActivity(activity: Activity) {
        ARouter.getInstance().build(RoutePath.Login.ACTIVITY_LOGIN).navigation(activity)
    }
}