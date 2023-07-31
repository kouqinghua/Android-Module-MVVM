package com.ktx.lib.api

import android.util.Log

data class BaseResult<T>(
    val code: String,
    val msg: String,
    val banner: String,
    val page: Int,
    val result: T?
) {

    fun getData(code: String = "200"): T {
        Log.e("response--->", result.toString())
        if (this.code == code) {
            result?.let {
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