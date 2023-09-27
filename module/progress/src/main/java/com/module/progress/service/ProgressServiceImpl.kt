package com.module.progress.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.common.router.RoutePath
import com.lib.common.router.service.progress.IProgressService

@Route(path = RoutePath.Progress.SERVICE_PROGRESS)
class ProgressServiceImpl : IProgressService {

    override fun init(context: Context?) {

    }
}