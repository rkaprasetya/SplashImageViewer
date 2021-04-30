package com.example.data.contextprovider

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val main : CoroutineDispatcher

    val default : CoroutineDispatcher

    val io : CoroutineDispatcher

    val unconfined : CoroutineDispatcher
}