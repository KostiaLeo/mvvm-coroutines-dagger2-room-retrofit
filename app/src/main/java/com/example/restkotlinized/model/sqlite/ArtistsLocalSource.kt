package com.example.restkotlinized.model.sqlite

import android.content.Context
import androidx.lifecycle.*
import com.example.restkotlinized.model.Results
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ArtistsLocalSource(context: Context) {

    private val repository: ArtistRepository
    private val allArtists: LiveData<ArrayList<Results>>

    init {
        val artistsDao = ArtistRoomDataBase
            .getDatabase(context).artistDao()
        repository = ArtistRepository(artistsDao)
        allArtists = Transformations.map(repository.allArtists) {
            ArrayList(it)
        }
    }

    suspend fun retrieveData(): ArrayList<Results> = suspendCoroutine {
        it.resume(allArtists.value!!)
    }


    suspend fun saveData(artists: ArrayList<Results>) {
        repository.refreshData(artists)
    }
}