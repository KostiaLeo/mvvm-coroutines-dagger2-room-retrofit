package com.example.restkotlinized.model.sqlite

import androidx.lifecycle.LiveData
import com.example.restkotlinized.model.Results
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ArtistRepository(private val artistDao: ArtistDao) {
    val allArtists: LiveData<List<Results>> = artistDao.getArtists()

    suspend fun refreshData(artists: ArrayList<Results>) {
        deleteAll()
        artistDao.insert(artists)
    }

    private suspend fun deleteAll(){
        artistDao.deleteAllArtists()
    }
}