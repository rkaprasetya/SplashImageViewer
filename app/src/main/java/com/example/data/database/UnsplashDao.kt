package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.ImageModel
import com.example.data.model.ListImages
import kotlinx.coroutines.flow.Flow

@Dao
interface UnsplashDao {
    @Query("SELECT * FROM images")
    fun getAllImages(): Flow<ListImages>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(vararg images: ImageModel)

    @Delete
    suspend fun deleteImage(vararg images : ImageModel)

    @Query("SELECT EXISTS(SELECT 1 FROM images WHERE id = :id LIMIT 1)")
    fun isImageExists(id:String):Flow<Boolean>
}