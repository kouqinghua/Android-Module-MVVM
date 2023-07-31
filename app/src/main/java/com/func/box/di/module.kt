package com.func.box.di

import com.func.box.vm.MainViewModel
import com.func.box.vm.MealViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    viewModel {
        MainViewModel()
    }

    viewModel {
        MealViewModel()
    }
}