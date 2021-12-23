package com.example.portfolio1.database.di

import com.example.portfolio1.webAPI.NetworkSource
import com.example.portfolio1.webAPI.RandomUserApi
import org.koin.dsl.module

val networkModule = module {
    single<NetworkSource>{
        RandomUserApi()
    }
}