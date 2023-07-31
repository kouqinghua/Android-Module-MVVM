package com.ktx.lib.api

data class ApiResult<T> (
        val code: Int,
        val msg: String,
        val show: Int,
        val page: Int,
        val result: T
) {
    companion object {
        const val CODE_SUCCESS = 200
    }

    fun getData(code: Int = 200): T {
        if (this.code == code) {
            return result
        } else {
            throw ApiException(code, msg)
        }
    }
}