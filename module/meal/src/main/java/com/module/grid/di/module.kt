package com.module.grid.di

import com.module.grid.viewmodel.MealViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

//    single {
//        LoginRepository(get())
//    }
//
    viewModel {
        MealViewModel()
    }
}