package com.ktx.lib.manager

import android.app.Activity
import android.os.Process
import java.util.*
import kotlin.system.exitProcess

class ActivityManager private constructor() {

    companion object {
        private var activityStack: Stack<Activity>? = null
        val instance: ActivityManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity?) {
        if (activityStack == null) {
            activityStack = Stack<Activity>()
        }
        activityStack!!.add(activity)
    }

    /**
     * 结束指定Activity
     */
    fun finishActivity(cls: Class<*>) {
        var i = 0
        val size: Int = activityStack!!.size
        while (i < size) {
            if (cls == activityStack!![i].javaClass) {
                val activity: Activity = activityStack!![i]
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
            i++
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size: Int = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                val activity: Activity = activityStack!![i]
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
            i++
        }
        activityStack!!.clear()
    }

    /**
     * 结束所有Activity
     */
    fun finishAllWithoutActivity(a: Activity) {
        var i = 0
        val size: Int = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                val activity: Activity = activityStack!![i]
                if (activity !== a) {
                    if (!activity.isFinishing) {
                        activity.finish()
                    }
                }
            }
            i++
        }
        activityStack!!.clear()
    }


    /**
     * 退出应用程序
     */
    fun exit() {
        try {
            finishAllActivity()
            Process.killProcess(Process.myPid())
            exitProcess(0)
        } catch (ignored: Exception) {
        }
    }
}