package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.ImageModel

@Database(entities = [ImageModel::class],version = 1,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun splashImageDao(): UnsplashDao
}