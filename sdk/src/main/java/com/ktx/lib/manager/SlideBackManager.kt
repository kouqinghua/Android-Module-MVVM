package com.ktx.lib.manager

import android.app.Activity
import com.ktx.lib.Init
import com.ktx.lib.utils.Utils
import com.parfoismeng.slidebacklib.SlideBack

class SlideBackManager {

    companion object {
        val instance: SlideBackManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SlideBackManager()
        }
    }

    fun slideBack(activity: Activity) {
        val activityName: String = Utils.getActivityName(activity)

        println("activityName：----> $activityName")

        if (activityName != Init.SLIDEBACK_SHIELD_ACTIVITY_NAME) {
            SlideBack.with(activity)
                .haveScroll(true)
                .callBack { activity.onBackPressed() }
                .register()
        }
    }
}