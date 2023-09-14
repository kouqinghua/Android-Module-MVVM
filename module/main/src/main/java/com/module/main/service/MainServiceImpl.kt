package com.module.main.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.common.router.RoutePath
import com.lib.common.router.service.main.IMainService

@Route(path = RoutePath.Main.SERVICE_MAIN, name = "Home Test")
class MainServiceImpl : IMainService {

    override fun getModuleName(): String = "module_main"

    override fun init(context: Context?) {

    }
}