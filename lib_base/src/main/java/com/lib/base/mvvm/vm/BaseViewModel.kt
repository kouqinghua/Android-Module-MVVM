package com.lib.base.mvvm.vm

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lib.base.network.UIState

open class BaseViewModel : ViewModel() {

    val loadState by lazy {
        MutableLiveData<UIState>()
    }

    open fun init(arguments: Bundle?) {

    }

    override fun onCleared() {
        super.onCleared()
    }
}