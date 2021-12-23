package com.example.portfolio1.database.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.portfolio1.database.repositories.UserRepository
import com.example.portfolio1.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    factory {
        UserRepository(get())
    }

    viewModel {
        MainViewModel(get())
    }
}