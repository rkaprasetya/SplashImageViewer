package com.example.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.api.UnsplashRemote
import com.example.data.database.UnsplashDao
import com.example.data.model.ImageModel
import com.example.data.model.Result
import com.example.data.repository.UnsplashRepository
import com.example.utils.makeSplashImage
import com.example.utils.shouldBeEmpty
import com.example.utils.shouldBeError
import com.example.utils.shouldBeLoading
import com.example.utils.shouldBeSuccess
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class UnsplashRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository : UnsplashRepository
    @MockK lateinit var remote : UnsplashRemote
    @RelaxedMockK lateinit var dao : UnsplashDao

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        repository = UnsplashRepository(remote,dao)


    }
    @Test
    fun favoriteImages_returnSuccess() = runBlocking {
        val images = listOf(makeSplashImage(), makeSplashImage(), makeSplashImage())
        val flow = flow { emit(images) }
        coEvery { dao.getAllImages() } returns flow

        val result = repository.favoriteImages.toList()[0]

        result.shouldBeSuccess(images)
    }

    @Test
    fun favoriteImages_returnsEmpty() = runBlocking {
        val images = emptyList<ImageModel>()
        val flow = flow { emit(images) }
        coEvery { dao.getAllImages() } returns flow

        val result = repository.favoriteImages.toList()[0]

        result.shouldBeEmpty()
    }

    @Test
    fun addFavorite_callsDao() = runBlocking {
        val image = makeSplashImage()
        repository.addFavorite(image)

        coVerify(exactly = 1){
            dao.insertImage(image)
        }
    }
    @Test
    fun removeFavorite_callsDao() = runBlocking {
        val image = makeSplashImage()
        repository.removeFavorite(image)

        coVerify(exactly = 1){
            dao.deleteImage(image)
        }
    }
    @Test
    fun getImages_returnSuccess() = runBlocking {
        val images = listOf(makeSplashImage(), makeSplashImage(), makeSplashImage())
        coEvery { remote.getImages(any()) }returns images

        val flow = repository.getImages("car")
        val results = flow.toList()

        results.shouldHaveSize(2)
        results[0].shouldBeLoading()
        results[1].shouldBeSuccess(images)
    }
    @Test
    fun getTopImages_returnsEmpty() = runBlocking {
        // Stub remote
        coEvery { remote.getImages(any()) } returns emptyList()

        // Call repository
        val flow = repository.getImages("pics")
        val results = flow.toList()

        // Assertions
        results.shouldHaveSize(2)
        results[0].shouldBeLoading()
        results[1].shouldBeEmpty()
    }
    @Test
    fun getTopImages_returnsError() = runBlocking {
        // Stub remote
        coEvery { remote.getImages(any()) } throws IOException()

        // Call repository
        val flow = repository.getImages("pics")
        val results = flow.toList()

        // Assertions
        results.shouldHaveSize(2)
        results[0].shouldBeLoading()
        results[1].shouldBeError()
    }
}