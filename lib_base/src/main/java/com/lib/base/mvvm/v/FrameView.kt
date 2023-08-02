package com.lib.base.mvvm.v

import androidx.viewbinding.ViewBinding

/**
 * View层基类抽象
 */
interface FrameView<V : ViewBinding> {

    /**
     * 初始化View
     */
    fun V.initView()

    /**
     * 初始化数据
     */
    fun initialize()

    /**
     * 用于在页面创建时进行请求接口
     */
    fun initRequestData()
}