package com.example.android_skills.model.sqlite

import android.content.Context
import androidx.lifecycle.*
import com.example.android_skills.model.Results
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ArtistsLocalSource @Inject constructor(context: Context) {

    private lateinit var allArtists: ArrayList<Results>
    private val artistsDao = ArtistRoomDataBase.getDatabase(context).artistDao()
    private val repository = ArtistRepository(artistsDao)

    suspend fun retrieveData(): ArrayList<Results> {
        allArtists = repository.getData()

        return suspendCoroutine { continuation ->
            continuation.resume(allArtists)
        }
    }

    suspend fun saveData(artists: ArrayList<Results>) {
        println("saving: ${artists[0].name}")
        repository.refreshData(artists)
    }
}