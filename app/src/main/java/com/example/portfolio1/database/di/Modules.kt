package com.example.portfolio1.database.di

import org.koin.core.module.Module

fun getApplicationModules() : List<Module> = listOf(networkModule, viewModelModule)