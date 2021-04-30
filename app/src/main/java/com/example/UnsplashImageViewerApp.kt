package com.example

import android.app.Application
import com.example.di.appModule
import com.example.di.databaseModule
import com.example.di.networkModule
import com.example.di.repositoryModule
import com.example.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class UnsplashImageViewerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@UnsplashImageViewerApp)
            modules(
                appModule,
                viewModelModule,
                repositoryModule,
                networkModule,
                databaseModule
            )
        }
    }
}