package com.lib.base.network

open class BaseResponse<T>(var data: T, var errorCode: Int = -1, var errorMsg: String? = null)