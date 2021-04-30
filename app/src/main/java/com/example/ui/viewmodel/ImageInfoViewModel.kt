package com.example.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.model.ImageModel
import com.example.data.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ImageInfoViewModel(
    private val repository: UnsplashRepository,
    val image: ImageModel
) : ViewModel() {
    val isFavorite : LiveData<Boolean> = repository.isFavorite(image.id.toString()).asLiveData()

    fun saveImage(){
        viewModelScope.launch {
            repository.addFavorite(image)
        }
    }
    fun removeImage(){
        viewModelScope.launch {
            repository.removeFavorite(image)
        }
    }
}