package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.model.ListImages
import com.example.data.model.Result
import com.example.data.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest

class ImageListViewModel(private val repository: UnsplashRepository):ViewModel() {
    private val query = MutableStateFlow("")

    val images : Flow<Result<ListImages>> = query
        // Discard text typed in a very short time to avoid many network calls
        .debounce(700L)
        // Filter empty text to avoid unnecessary network call
        .filter { text->
            text.isNotEmpty()
        }
        // When a new text is set, transform it in Result<RedditImages> by triggering a
        // network call
        .flatMapLatest { text ->
            repository.getImages(text)
        }

    fun setQuery(text:String){
        query.value = text
    }
}