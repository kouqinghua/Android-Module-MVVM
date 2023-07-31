package com.ktx.lib.utils

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import com.gyf.barlibrary.ImmersionBar
import com.ktx.lib.R

object StatusBar {

    fun init(activity: Activity, toolbar: Toolbar) {
        ImmersionBar
            .with(activity)
            .statusBarDarkFont(true)
            .navigationBarColor(R.color.trans)
            .navigationBarAlpha(1f)
            .fullScreen(false)
            .init()
        ImmersionBar.setTitleBar(activity, toolbar)
    }
}