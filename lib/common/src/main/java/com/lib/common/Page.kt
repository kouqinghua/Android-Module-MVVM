package com.lib.common

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_common.R

val defaultPageIn = R.anim.from_left_in
val defaultPageOut = R.anim.from_left_out

fun Activity.pageTransition(){
    overridePendingTransition(defaultPageIn, defaultPageOut)
}

fun start(path: String) {
    ARouter.getInstance().build(path).navigation()
}