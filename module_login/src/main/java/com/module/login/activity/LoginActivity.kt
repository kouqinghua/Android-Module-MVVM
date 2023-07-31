package com.module.login.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.lib.common.PagePath
import com.lib.common.net.RetrofitClient
import com.module.login.R

@Route(path = PagePath.login)
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_layout)

//        RetrofitClient.retrofit.create()
    }
}