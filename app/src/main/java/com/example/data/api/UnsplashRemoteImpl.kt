package com.example.data.api

import com.example.data.contextprovider.DispatcherProvider
import com.example.data.model.ImageModel
import kotlinx.coroutines.withContext

class UnsplashRemoteImpl(
    private val service: UnsplashService,
    private val dispatchers: DispatcherProvider
) : UnsplashRemote {
    override suspend fun getImages(query: String) = withContext(dispatchers.io) {
        val response = service.searchPhotos(query, page = 1, perPage = 10)

        val children = response.results

        children?.mapNotNull { it?.toImageModel() }
    }

    private fun ResultsItem.toImageModel(): ImageModel? {
        return if (urls != null) {
            ImageModel(username = user?.username ?: "", urlPhoto = urls.regular ?: "")
        } else {
            null
        }
    }
}