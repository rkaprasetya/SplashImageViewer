package com.example.di

import com.example.data.model.ImageModel
import com.example.data.repository.UnsplashRepository
import com.example.ui.viewmodel.CollectionViewModel
import com.example.ui.viewmodel.ImageInfoViewModel
import com.example.ui.viewmodel.ImageListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { provideImageListViewModel(get()) }
    viewModel { proviceCollectionViewModel(get()) }
    viewModel { (image: ImageModel) -> provideImageInfoViewModel(get(), image) }
}

private fun provideImageListViewModel(repository: UnsplashRepository): ImageListViewModel {
    return ImageListViewModel(repository)
}

private fun proviceCollectionViewModel(repository: UnsplashRepository): CollectionViewModel {
    return CollectionViewModel((repository))
}

private fun provideImageInfoViewModel(
    repository: UnsplashRepository,
    image: ImageModel
): ImageInfoViewModel = ImageInfoViewModel(repository, image)