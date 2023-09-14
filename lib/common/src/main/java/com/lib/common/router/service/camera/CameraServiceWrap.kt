package com.lib.common.router.service.camera

import android.app.Activity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.common.router.RoutePath
import com.lib.common.router.service.BaseServiceWrap

class CameraServiceWrap private constructor() : BaseServiceWrap() {

    @Autowired(name = RoutePath.Camera.SERVICE_CAMERA)
    lateinit var service: ICameraService

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CameraServiceWrap()
        }
    }

    fun toCameraActivity(activity: Activity) {
        ARouter.getInstance().build(RoutePath.Camera.ACTIVITY_CAMERA).navigation(activity)
    }

}