package com.ktx.lib.api

import android.util.Log

class BaseResp <T>(
    val code: String,
    val msg: String,
    val banner: String,
    val page: Int,
    val data: T?
) {

    fun getData(code: String = "200"): T {
        Log.e("response--->", data.toString())
        if (this.code == code) {
            data?.let {
                return it
            }
        } else {
            throw ResultException(this.code, msg)
        }

        throw ResultException(this.code, msg)
    }

    fun getCode(): Boolean {
        if (code != "200") {
            throw ResultException(this.code, msg)
        }

        return true
    }
}