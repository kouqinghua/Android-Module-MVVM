package com.lib.base.network.state

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * 自动注册网络状态监听
 * 使用的是[androidx.lifecycle.LifecycleObserver]来同步生命周期
 */
class AutoRegisterNetListener constructor(listener: NetworkStateChangeListener) :
    LifecycleEventObserver {

    /**
     * 当前需要自动注册的监听器
     */
    private var mListener: NetworkStateChangeListener? = null

    init {
        mListener = listener
    }



    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> mListener?.run { NetworkStateClient.setListener(this) }
            Lifecycle.Event.ON_PAUSE -> NetworkStateClient.removeListener()
            Lifecycle.Event.ON_DESTROY -> {
                NetworkStateClient.removeListener()
                mListener = null
            }
            else -> {}
        }
    }
}