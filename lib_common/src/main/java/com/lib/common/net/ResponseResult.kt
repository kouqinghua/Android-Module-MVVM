package com.lib.common.net

import com.google.gson.annotations.SerializedName

data class ResponseResult<T>(
    @SerializedName("errorCode") var errorCode: Int = -1,
    @SerializedName("errorMsg") var errorMsg: String? = "",
    @SerializedName("data") var data: T? = null
)
