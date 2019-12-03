package com.example.restkotlinized.model.sqlite

import androidx.lifecycle.LiveData
import com.example.restkotlinized.model.Results

class ArtistRepository(private val artistDao: ArtistDao) {
    val allArtists: LiveData<List<Results>> = artistDao.getArtists()

    suspend fun insert(artist: Results) {
        artistDao.insert(artist)
    }
}