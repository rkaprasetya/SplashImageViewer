package com.example.data.network

import com.example.data.api.UnsplashRemoteImpl
import com.example.data.api.UnsplashService
import com.example.utils.CoroutineTestRule
import com.example.utils.makeSplashImage
import com.example.utils.makeSplashPost
import com.example.utils.makeSplashResponse
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UnsplashRemoteImplTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var remote : UnsplashRemoteImpl
    @MockK lateinit var service : UnsplashService

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        remote = UnsplashRemoteImpl(service,coroutineTestRule.testDispatcherProvider)
    }
    @Test
    fun getImages_returnImageList() = coroutineTestRule.runBlockingTest {

        val response = makeSplashResponse()
        coEvery { service.searchPhotos(any(),1,10) } returns response

        val images = remote.getImages("car")

        val expected = listOf(makeSplashImage())
        images.shouldContainExactly(expected)
    }
}