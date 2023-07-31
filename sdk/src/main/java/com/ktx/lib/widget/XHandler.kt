package com.ktx.lib.widget

import android.app.Activity
import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

@Suppress("DEPRECATION")
class XHandler(val activity: Activity?, private val iHandler: HandlerBack) : Handler() {

    private val wr: WeakReference<Activity> by lazy {
        WeakReference(activity)
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        if (wr.get() == null || wr.get()!!.isFinishing) {
            return
        }

        iHandler.handleMessage(msg)
    }

    interface HandlerBack {
        fun handleMessage(msg: Message?)
    }
}