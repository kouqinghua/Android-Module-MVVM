package com.lib.base.utils

import android.app.Activity
import androidx.core.content.ContextCompat
import com.gyf.immersionbar.ktx.immersionBar

/**
 * 状态栏文字模式切换, 并不会透明状态栏, 只是单纯的状态栏文字变暗色调.
 * @param darkMode 状态栏文字是否为暗色
 */
@JvmOverloads
fun Activity.darkMode(darkMode: Boolean = true) {
    immersionBar {
        fitsSystemWindows(true)
        statusBarDarkFont(darkMode)
        statusBarColor(if (darkMode) "#ffffff" else "#000000")
    }
}

// 设置状态栏颜色
fun Activity.statusBarColor(color: Int) {
    window?.statusBarColor = ContextCompat.getColor(this, color)
}