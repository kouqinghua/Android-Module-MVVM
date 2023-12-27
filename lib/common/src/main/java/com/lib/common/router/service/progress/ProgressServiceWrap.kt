package com.lib.common.router.service.progress

import android.app.Activity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.common.router.RoutePath
import com.lib.common.router.service.BaseServiceWrap

class ProgressServiceWrap private constructor() : BaseServiceWrap() {

    @Autowired(name = RoutePath.Progress.SERVICE_PROGRESS)
    lateinit var service: IProgressService

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ProgressServiceWrap()
        }
    }

    fun toProgressActivity(activity: Activity) {
        ARouter.getInstance().build(RoutePath.Progress.ACTIVITY_PROGRESS).navigation(activity)
    }

}