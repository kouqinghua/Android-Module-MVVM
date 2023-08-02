package com.lib.base.utils

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.lib.base.app.BaseApplication

inline val Double.dp: Int
    get() = run {
        return toFloat().dp
    }

inline val Int.dp: Int
    get() = run {
        return toFloat().dp
    }

inline val Float.dp: Int
    get() = run {
        val scale: Float = BaseApplication.context.resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

inline val String.int: Int
    get() = run {
        return toInt()
    }

inline val String.float: Float
    get() = run {
        return toFloat()
    }

fun Context.console(str: Any?) {
    Log.e("${this.javaClass.name}====>默认Log", "$str")
}

fun Fragment.console(str: Any?) {
    Log.e("${this.javaClass.name}====>默认Log", "$str")
}

fun Context.console(str: Any?, tag: String = "自定义Log") {
    Log.e("${this.javaClass.name}====>$tag", "$str")
}
