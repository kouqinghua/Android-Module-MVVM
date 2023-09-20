package com.module.grid.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.common.router.RoutePath
import com.lib.common.router.service.camera.ICameraService
import com.lib.common.router.service.grid.IGridService
import com.lib.common.router.service.login.ILoginService
import com.lib.common.router.service.main.IMainService

@Route(path = RoutePath.Grid.SERVICE_GRID)
class GridServiceImpl : IGridService {

    override fun init(context: Context?) {

    }
}