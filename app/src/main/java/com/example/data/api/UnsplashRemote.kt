package com.example.data.api

import com.example.data.model.ListImages

interface UnsplashRemote {
    suspend fun getImages(query: String): ListImages
}