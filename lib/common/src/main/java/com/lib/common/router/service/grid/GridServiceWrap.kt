package com.lib.common.router.service.grid

import android.app.Activity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.lib.common.router.RoutePath
import com.lib.common.router.service.BaseServiceWrap

class GridServiceWrap private constructor() : BaseServiceWrap() {

    @Autowired(name = RoutePath.Grid.SERVICE_GRID)
    lateinit var service: IGridService

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            GridServiceWrap()
        }
    }

    fun toGridActivity(activity: Activity) {
        ARouter.getInstance().build(RoutePath.Grid.ACTIVITY_GRID).navigation(activity)
    }

}