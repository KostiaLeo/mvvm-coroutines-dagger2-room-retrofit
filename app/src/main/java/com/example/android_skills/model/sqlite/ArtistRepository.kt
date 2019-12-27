package com.example.android_skills.model.sqlite

import androidx.lifecycle.LiveData
import com.example.android_skills.model.Results

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