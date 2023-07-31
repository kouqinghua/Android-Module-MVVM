package com.ktx.lib.utils

import android.app.Activity
import android.os.Handler
import com.ktx.lib.widget.XHandler


@Suppress("DEPRECATION")
class UHandler {

    companion object {
        fun postDelayed(runnable: Runnable) {
            Handler().postDelayed(runnable, 1500);
        }

        fun postDelayed(s: Long, runnable: Runnable) {
            Handler().postDelayed(runnable, s)
        }

        fun handleMessage(activity: Activity, handlerBack: XHandler.HandlerBack): XHandler {
            return XHandler(activity, handlerBack)
        }

        fun handleDestroy(handler: Handler) {
            handler.removeCallbacksAndMessages(null)
        }
    }
}