package com.lib.common.net

class BaseResp<T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T?
) {

    fun getData(code: Int = 0): T {
        if (this.errorCode == code) {
            data?.let {
                return it
            }
        }

        throw Exception(errorMsg)
    }

}