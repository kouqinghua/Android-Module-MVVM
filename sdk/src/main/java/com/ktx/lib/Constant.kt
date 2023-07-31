package com.ktx.lib

import android.Manifest

class Constant {

    companion object {
        const val APP_VERSION = "APP_VERSION"
        const val ON_LINE = "ON_LINE"

        //是否展示隐私政策提示框标志
        const val PRIVACY_SHOW = "PRIVACY_SHOW"
        const val NEW_VERSION = "NEW_VERSION"

        //是否是splash页面进入
        const val SPLASH_ENTER = "SPLASH_ENTER"

        const val AVATAR = "AVATAR"
        const val TOKEN = "TOKEN"
        const val NICK_NAME = "NICK_NAME"
        const val LEVEL = "LEVEL"
        const val PHONE = "PHONE"

        const val GT_CLIENT_ID = "GT_CLIENT_ID"
        const val ALI_LOGIN = "ALI_LOGIN"
        const val ALI_ACCESSTOKEN = "ALI_ACCESSTOKEN"

        const val BASE_HTTP = "http://yht.gldianzisw.com/"

        const val DEFAULT_LONGITUDE = "116.352963"
        const val DEFAULT_LATITUDE = "40.409079"

        //------------------------------微信相关-------------------------------
        const val WX_APP_ID = "wx0bc7e156e4274e9b"
        const val WX_APP_SECRET = "59e465543909cb9d826988c378b470d0"
        const val WX_OPEN_ID = "wx_open_id"
        const val WX_ACCESS_TOKEN = "wx_access_token"

        //------------------------------权限相关-------------------------------

        /**
         * 相机所需权限列表
         */
        val CAMERA_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        /**
         * 相机所需权限列表
         */
        val PHOTO_PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        //打电话 相关权限
        val CALL_PERMISSIONS = arrayOf(
            Manifest.permission.CALL_PHONE
        )

        //高德地图所需权限
        var GD_MAP = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}