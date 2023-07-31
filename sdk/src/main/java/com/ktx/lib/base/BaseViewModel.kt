package com.ktx.lib.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(), LifecycleObserver {

    val mLoadState = MutableLiveData<LoadState>()

    public fun changeState(state: LoadState) {
        when (state) {
            LoadState.LOADING -> {
                mLoadState.postValue(LoadState.LOADING)
            }
            LoadState.SUCCESS -> {
                mLoadState.postValue(LoadState.SUCCESS)
            }
            LoadState.COMPLETE -> {
                mLoadState.postValue(LoadState.COMPLETE)
            }
            LoadState.EMPTY -> {
                mLoadState.postValue(LoadState.EMPTY)
            }
            LoadState.ERROR -> {
                mLoadState.postValue(LoadState.ERROR)
            }
            LoadState.NET_ERROR -> {
                mLoadState.postValue(LoadState.NET_ERROR)
            }
            LoadState.HIDE -> {
                mLoadState.postValue(LoadState.HIDE)
            }
        }
    }
}