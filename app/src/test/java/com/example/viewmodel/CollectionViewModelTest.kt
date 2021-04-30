package com.example.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.model.ListImages
import com.example.data.model.Result
import com.example.data.repository.UnsplashRepository
import com.example.ui.viewmodel.CollectionViewModel
import com.example.utils.CoroutineTestRule
import com.example.utils.makeSplashImage
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.apache.tools.ant.types.Assertions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert

class CollectionViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel : CollectionViewModel
    @MockK lateinit var repository : UnsplashRepository

    @Before
    fun setup(){
        MockKAnnotations.init(this)
    }
    @Test
    fun favoriteImages_returnSuccess()=coroutineTestRule.runBlockingTest {
        val images = listOf(makeSplashImage(), makeSplashImage(), makeSplashImage())
        val flow = flow { emit(Result.success(images)) }
        every { repository.favoriteImages } returns flow
        val list = mutableListOf<Result<ListImages>>()
        viewModel = CollectionViewModel(repository)
        val vmFlow = viewModel.favoriteImages
        vmFlow.take(1).collect {
            list.add(it)
        }
    }
}