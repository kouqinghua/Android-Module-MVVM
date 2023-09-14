package com.module.login.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.common.router.RoutePath
import com.lib.common.router.service.login.ILoginService

@Route(path = RoutePath.Login.SERVICE_LOGIN, name = "Login Test")
class LoginServiceImpl : ILoginService {

    override fun init(context: Context?) {

    }
}