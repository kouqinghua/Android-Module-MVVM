package com.module.progress.di

import com.module.progress.viewmodel.ProgressViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {


    viewModel {
        ProgressViewModel()
    }
}