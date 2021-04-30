package com.example.di

import android.app.Application
import androidx.room.Room
import com.example.data.database.AppDatabase
import com.example.data.database.UnsplashDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val DATABASE_NAME = "splash_db"

val databaseModule = module {
    single { provideAppDatabase(androidApplication()) }

    single { provideSplashDao(get()) }
}

private fun provideAppDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME).build()
}
private fun provideSplashDao(database: AppDatabase): UnsplashDao{
    return database.splashImageDao()
}