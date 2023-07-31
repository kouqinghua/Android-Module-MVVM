package com.ktx.lib.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

private const val COLOR_TRANSPARENT = 0

/**
 * 状态栏高度
 */
val Context?.statusBarHeight: Int
    get() {
        this ?: return 0
        var result = 24
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        result = if (resId > 0) {
            resources.getDimensionPixelSize(resId)
        } else {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                result.toFloat(), Resources.getSystem().displayMetrics
            ).toInt()
        }
        return result
    }

/**
 * 设置状态栏颜色
 */
fun Activity.statusBarColor(@ColorInt color: Int) {
    window?.statusBarColor = color
}

/**
 * 设置状态栏颜色
 */
fun Fragment.statusBarColor(@ColorInt color: Int) = activity?.statusBarColor(color)

/**
 * 获取颜色资源值来设置状态栏
 */
fun Activity.statusBarColorRes(@ColorRes colorRes: Int) = statusBarColor(resources.getColor(colorRes))

/**
 * 获取颜色资源值来设置状态栏
 */
fun Fragment.statusBarColorRes(@ColorRes colorRes: Int) = activity?.statusBarColorRes(colorRes)

/**
 * 使用视图的背景色作为状态栏颜色
 */
@JvmOverloads
fun Activity.immersive(view: View, darkMode: Boolean? = null) {
    val background = view.background
    if (background is ColorDrawable) {
        immersive(background.color, darkMode)
    }
}

/**
 * 设置透明状态栏或者状态栏颜色, 会导致状态栏覆盖界面
 * 如果不指定状态栏颜色则会应用透明状态栏(全屏属性), 会导致键盘遮挡输入框
 * @param color 状态栏颜色, 不指定则为透明状态栏
 * @param darkMode 是否显示暗色状态栏文字颜色
 */
@SuppressLint("ObsoleteSdkInt")
@JvmOverloads
fun Activity.immersive(@ColorInt color: Int = COLOR_TRANSPARENT, darkMode: Boolean? = null) {
    when {
        Build.VERSION.SDK_INT >= 21 -> {
            when (color) {
                COLOR_TRANSPARENT -> {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    var systemUiVisibility = window.decorView.systemUiVisibility
                    systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    window.decorView.systemUiVisibility = systemUiVisibility
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = color
                }
                else -> {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    var systemUiVisibility = window.decorView.systemUiVisibility
                    systemUiVisibility =
                        systemUiVisibility and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    systemUiVisibility = systemUiVisibility and View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    window.decorView.systemUiVisibility = systemUiVisibility
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = color
                }
            }
        }
        Build.VERSION.SDK_INT >= 19 -> {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (color != COLOR_TRANSPARENT) {
                setTranslucentView(window.decorView as ViewGroup, color)
            }
        }
    }
    if (darkMode != null) {
        darkMode(darkMode)
    }
}

/**
 * 创建自定义透明栏
 */
private fun Context.setTranslucentView(container: ViewGroup, color: Int) {
    var simulateStatusBar: View? = container.findViewById(android.R.id.custom)
    if (simulateStatusBar == null && color != 0) {
        simulateStatusBar = View(container.context)
        simulateStatusBar.id = android.R.id.custom
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
        container.addView(simulateStatusBar, lp)
    }
    simulateStatusBar?.setBackgroundColor(color)
}

/**
 * 状态栏文字模式切换, 并不会透明状态栏, 只是单纯的状态栏文字变暗色调.
 * @param darkMode 状态栏文字是否为暗色
 */
@JvmOverloads
fun Activity.darkMode(darkMode: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var systemUiVisibility = window.decorView.systemUiVisibility
        systemUiVisibility = if (darkMode) {
            systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        window.decorView.systemUiVisibility = systemUiVisibility
    }
}

/**
 * 设置是否全屏
 * @param enabled 是否全屏显示
 */
@JvmOverloads
fun Activity.setFullscreen(enabled: Boolean = true) {
    val systemUiVisibility = window.decorView.systemUiVisibility
    window.decorView.systemUiVisibility = if (enabled) {
        systemUiVisibility or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    } else {
        systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
    }
}