package com.func.box.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.func.box.R
import com.lib.common.router.service.main.MainServiceWrap

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.app_activity)

        Handler(Looper.getMainLooper()).postDelayed({
            MainServiceWrap.instance.toMainTabActivity(this)
            finish()
        }, 1500)
    }
}