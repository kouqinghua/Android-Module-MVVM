package com.module.login.di

import androidx.lifecycle.MutableLiveData
import com.lib.base.network.UIState
import com.module.login.repository.LoginRepository
import com.module.login.viewmodel.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    single {
        LoginRepository(get())
    }

    viewModel {
        LoginViewModel(get())
    }
}