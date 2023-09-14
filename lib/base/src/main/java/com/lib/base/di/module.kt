package com.lib.base.di

import androidx.lifecycle.MutableLiveData
import com.lib.base.network.UIState
import org.koin.dsl.module

val module = module {

    single {
        MutableLiveData<UIState>()
    }
}