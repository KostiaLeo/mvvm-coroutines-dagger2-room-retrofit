package com.example.restkotlinized.model.sqlite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.restkotlinized.model.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LiveDataProvider(context: Context, private val viewModelScope: CoroutineScope) {
    private val repository: ArtistRepository
    val allArtists: LiveData<ArrayList<Results>>

    init {
        val artistsDao = ArtistRoomDataBase.getDatabase(
            context,
            viewModelScope
        ).artistDao()
        repository = ArtistRepository(artistsDao)
        allArtists = Transformations.map(repository.allArtists) {
            ArrayList(it)
        }
    }

    fun insert(artists: ArrayList<Results>) = viewModelScope.launch {
        repository.refreshData(artists)
    }
}