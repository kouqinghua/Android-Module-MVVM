package com.func.box.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.func.box.databinding.ActivitySplashBinding
import com.lib.base.mvvm.v.BaseActivity
import com.lib.base.mvvm.vm.BaseViewModel
import com.lib.common.router.service.main.MainServiceWrap

class SplashActivity : BaseActivity<ActivitySplashBinding, BaseViewModel>() {


    private var loadingTime = 1
    private var isLoadFinish = false

    private var timer: CountDownTimer = object : CountDownTimer(loadingTime * 1000L, 1000) {
        override fun onTick(sin: Long) {
            loadingTime = (sin / 1000).toInt()
        }

        override fun onFinish() {
            isLoadFinish = true
        }
    }

    override val mViewModel by viewModels<BaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        // 不显示系统的标题栏，保证windowBackground和界面activity_main的大小一样，
        // 显示在屏幕不会有错位（去掉这一行试试就知道效果了）
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Keep the splash screen visible for this Activity
        splashScreen.setKeepOnScreenCondition { true }

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                // Check if the initial data is ready.
                return if (isLoadFinish) {
                    // The content is ready; start drawing.
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    startHome()
                    true
                } else {
                    // The content is not ready; suspend.
                    false
                }
            }
        })
    }

    private fun startHome() {
//        WifiServiceWrap.instance.toWifiActivity(this)
        MainServiceWrap.instance.toMainTabActivity(this)
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 100)
    }

    override fun ActivitySplashBinding.initView() {}

    override fun initialize() {}

    override fun initObserve() {
        timer.start()
    }

    override fun initRequestData() {}

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }
}