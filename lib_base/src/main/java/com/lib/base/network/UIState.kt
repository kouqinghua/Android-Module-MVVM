package com.lib.base.network

/**
 * code: 状态码
 * message: 错误消息
 * key: 用于区分不同请求的key
 */
data class UIState(
    var stateType: UIStateType,
    var code: Int = 200,
    var msg: String? = null,
    var key: String? = null)