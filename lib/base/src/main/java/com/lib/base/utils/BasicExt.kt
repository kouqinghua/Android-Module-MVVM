package com.lib.base.utils

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.lib.base.app.BaseApplication
import com.lib.base.network.log.LogCat

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

fun console(str: Any?) {
    LogCat.e("$str", "====>默认Log")
}

fun console(str: Any?, tag: String = "自定义Log") {
    LogCat.e("$str", "====>$tag")
}
