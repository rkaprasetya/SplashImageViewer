package com.example.data.repository

import androidx.lifecycle.LiveData
import com.example.data.api.UnsplashRemote
import com.example.data.database.UnsplashDao
import com.example.data.model.ImageModel
import com.example.data.model.ListImages
import com.example.data.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UnsplashRepository(
    private val unsplashService: UnsplashRemote,
    private val dao: UnsplashDao
) {

    /**
     * fetch list of favorite image from database
     */
    val favoriteImages : Flow<Result<ListImages>>
    get() = dao.getAllImages().map { images ->
        Result.successOrEmpty(images!!)
    }

    /**
     * add a favorite image to database
     */
    suspend fun addFavorite(image:ImageModel){
        dao.insertImage(image)
    }

    /**
     * remove a favorite image
     */
    suspend fun removeFavorite(image: ImageModel){
        dao.deleteImage(image)
    }

    /**
     * check if an image is favorite
     */
    fun isFavorite(id:String): Flow<Boolean>{
        return dao.isImageExists(id)
    }

    /**
     * search list of images on remote source
     */
    fun getImages(query:String):Flow<Result<ListImages>> = flow {
        emit(Result.loading())

        val images = unsplashService.getImages(query)
        emit(Result.successOrEmpty(images!!))
    }.catch { e ->
        emit(Result.error(e))
    }
}