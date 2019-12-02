package com.example.restkotlinized.model.sqlite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.restkotlinized.model.remote.Results
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository
    val allArtists: LiveData<List<Results>>

    init {
        val artistsDao = ProductRoomDataBase.getDatabase(
            application,
            viewModelScope
        ).artistDao()
        repository = ProductRepository(artistsDao)
        allArtists = repository.allArtists
    }

    fun insert(artist: Results) = viewModelScope.launch {
        repository.insert(artist)
    }
}