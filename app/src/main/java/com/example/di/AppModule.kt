package com.example.di

import com.example.data.contextprovider.DefaultDispatcherProvider
import com.example.data.contextprovider.DispatcherProvider
import org.koin.dsl.module

val appModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }
}