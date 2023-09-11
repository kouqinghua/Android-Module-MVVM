package com.module.camera.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.common.router.RoutePath
import com.lib.common.router.service.camera.ICameraService
import com.lib.common.router.service.login.ILoginService
import com.lib.common.router.service.main.IMainService

@Route(path = RoutePath.Camera.SERVICE_CAMERA)
class CameraServiceImpl : ICameraService {

    override fun init(context: Context?) {

    }
}