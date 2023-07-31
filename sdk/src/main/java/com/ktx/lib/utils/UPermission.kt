package com.ktx.lib.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class UPermission {

    companion object{
        /**
         * 检测所有的权限是否都已授权
         */
        fun checkPermissions(c: Context, permissions: Array<String>): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true
            }
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(c, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }

        /**
         * 获取权限集中需要申请权限的列表
         */
        fun getDeniedPermissions(a: Activity, permissions: Array<String>): List<String> {
            val needRequestPermissionList: MutableList<String> = ArrayList()
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(a, permission) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(
                        a,
                        permission
                    )) {
                    needRequestPermissionList.add(permission)
                }
            }
            return needRequestPermissionList
        }

        /**
         * 确认所有的权限是否都已授权
         */
        fun verifyPermissions(grantResults: IntArray): Boolean {
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }
    }
}