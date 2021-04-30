package com.example.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.data.model.ListImages
import com.example.data.model.Result
import com.example.data.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow

class CollectionViewModel(repository: UnsplashRepository): ViewModel() {
    val favoriteImages : Flow<Result<ListImages>> = repository.favoriteImages
}