package com.module.main.di

import com.module.main.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    viewModel {
        MainViewModel()
    }
}