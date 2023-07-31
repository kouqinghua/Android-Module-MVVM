package com.ktx.lib.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.TypedValue
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class Utils {

    companion object {
        fun getActivityName(context: Context): String {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningTasks = manager.getRunningTasks(1)
            val component = runningTasks[0].topActivity
            return component!!.className
        }

        fun md5(str: String): String? {
            try {
                val m = MessageDigest.getInstance("MD5")
                m.update(str.toByteArray(StandardCharsets.UTF_8))
                val s = m.digest()
                val result = StringBuilder()
                for (b in s) {
                    result.append(
                        Integer.toHexString(0x000000FF and b.toInt() or -0x100).substring(
                            6
                        )
                    )
                }
                return result.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * 检测程序是否安装
         */
        @SuppressLint("QueryPermissionsNeeded")
        fun isInstalledMap(manager: PackageManager, packageName: String): Boolean {
            //获取所有已安装程序的包信息
            val installedPackages = manager.getInstalledPackages(0)
            for (info in installedPackages) {
                if (info.packageName == packageName) {
                    return true
                }
            }

            return false
        }

        /** dp转px  */
        fun dp2px(context: Context, dpVal: Float): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpVal,
                context.resources.displayMetrics
            )
                .toInt()
        }
    }
}