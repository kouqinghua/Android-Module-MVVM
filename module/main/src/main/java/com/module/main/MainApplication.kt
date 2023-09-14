package com.module.main

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import com.lib.base.app.ApplicationLifecycle
import com.lib.base.utils.ProcessUtils
import com.module.main.di.module
import org.koin.core.context.loadKoinModules

@AutoService(ApplicationLifecycle::class)
class MainApplication : ApplicationLifecycle {

    private lateinit var application: Application

    companion object {
        // 全局CommonApplication
        @SuppressLint("StaticFieldLeak")
        lateinit var mMainApplication: MainApplication
    }

    override fun onAttachBaseContext(context: Context) {
        mMainApplication = this
    }

    override fun onCreate(application: Application) {
        this.application = application
    }

    override fun onTerminate(application: Application) {

    }

    override fun initByFrontDesk(): MutableList<() -> String> {
        val list = mutableListOf<() -> String>()
        // 以下只需要在主进程当中初始化 按需要调整
        if (ProcessUtils.isMainProcess(application)) {
            list.add { loadKoinModules() }
        }
        return list
    }

    override fun initByBackstage() {

    }


    /**
     * initKoin 初始化
     */
    private fun loadKoinModules(): String {
        loadKoinModules(module)
        return "${javaClass}-->> Koin -->> init complete"
    }
}