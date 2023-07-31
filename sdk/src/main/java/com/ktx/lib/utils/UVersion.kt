package com.ktx.lib.utils

import android.content.Context
import android.content.pm.PackageManager

class UVersion {

    companion object {

        fun getVerName(context: Context): String {
            var verName: String? = "1.0"
            try {
                verName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return verName!!
        }
    }
}