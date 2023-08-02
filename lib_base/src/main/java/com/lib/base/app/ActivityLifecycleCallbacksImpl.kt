package com.lib.base.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.lib.base.network.log.LogCat

class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {

    private val TAG = "ActivityLifecycle"

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        ActivityStackManager.addActivityToStack(activity)
        LogCat.e("${activity.javaClass.simpleName} --> onActivityCreated", TAG)
    }

    override fun onActivityStarted(activity: Activity) {
        LogCat.e("${activity.javaClass.simpleName} --> onActivityStarted", TAG)
    }

    override fun onActivityResumed(activity: Activity) {
        LogCat.e("${activity.javaClass.simpleName} --> onActivityResumed", TAG)
    }

    override fun onActivityPaused(activity: Activity) {
        LogCat.e("${activity.javaClass.simpleName} --> onActivityPaused", TAG)
    }

    override fun onActivityStopped(activity: Activity) {
        LogCat.e("${activity.javaClass.simpleName} --> onActivityStopped", TAG)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        LogCat.e("${activity.javaClass.simpleName} --> onActivitySaveInstanceState", TAG)
    }

    override fun onActivityDestroyed(activity: Activity) {
        ActivityStackManager.popActivityToStack(activity)
        LogCat.e("${activity.javaClass.simpleName} --> onActivityDestroyed", TAG)
    }
}