package com.module.camera

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraXConfig
import com.google.auto.service.AutoService
import com.lib.base.app.ApplicationLifecycle
import com.lib.base.utils.ProcessUtils
import com.lib.base.utils.console
import com.module.camera.di.module
import org.koin.core.context.loadKoinModules

@AutoService(ApplicationLifecycle::class)
class CameraApplication : ApplicationLifecycle, CameraXConfig.Provider {

    private lateinit var application: Application

    companion object {
        // 全局CommonApplication
        @SuppressLint("StaticFieldLeak")
        lateinit var mLoginApplication: CameraApplication
    }

    override fun onAttachBaseContext(context: Context) {
        mLoginApplication = this
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

    override fun getCameraXConfig(): CameraXConfig {
        console("相机配置代码执行了------->")
        return CameraXConfig.Builder.fromConfig(Camera2Config.defaultConfig())
            .setAvailableCamerasLimiter(CameraSelector.DEFAULT_BACK_CAMERA)
            .build()
    }
}