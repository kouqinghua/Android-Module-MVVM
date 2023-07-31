package com.lib.common.base

enum class LoadState {
    LOADING, // 加载中
    COMPLETE, // 加载完成
    SUCCESS, // 数据成功
    ERROR, // 加载错误
    EMPTY, // 数据为空
    NET_ERROR, // 网络错误
    HIDE // 隐藏框框
}