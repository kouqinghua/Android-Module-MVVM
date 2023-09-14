package com.module.camera.di

import com.module.camera.vm.CameraViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

//    single {
//        LoginRepository(get())
//    }
//
    viewModel {
        CameraViewModel()
    }
}