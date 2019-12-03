package com.example.restkotlinized.model.sqlite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.restkotlinized.model.Results
import kotlinx.coroutines.launch

class ArtistViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ArtistRepository
    val allArtists: LiveData<List<Results>>

    init {
        val artistsDao = ArtistRoomDataBase.getDatabase(
            application,
            viewModelScope
        ).artistDao()
        repository = ArtistRepository(artistsDao)
        allArtists = repository.allArtists
    }

    fun insert(artist: Results) = viewModelScope.launch {
        repository.insert(artist)
    }
}