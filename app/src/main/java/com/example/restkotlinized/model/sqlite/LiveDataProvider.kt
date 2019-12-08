package com.example.restkotlinized.model.sqlite

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.restkotlinized.model.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LiveDataProvider(context: Context, private val viewModelScope: CoroutineScope) {
    private val repository: ArtistRepository
    val allArtists: LiveData<List<Results>>

    init {
        val artistsDao = ArtistRoomDataBase.getDatabase(
            context,
            viewModelScope
        ).artistDao()
        repository = ArtistRepository(artistsDao)
        allArtists = repository.allArtists
    }

    fun insert(artist: Results) = viewModelScope.launch {
        repository.insert(artist)
    }
}