package com.module.main.di

import androidx.lifecycle.MutableLiveData
import com.lib.base.network.UIState
import com.module.main.repository.MainRepository
import com.module.main.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single {
        MainRepository(get())
    }

    viewModel {
        MainViewModel(get())
    }
}