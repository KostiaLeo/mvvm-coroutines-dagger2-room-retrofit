package com.example.android_skills.model.sqlite

import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.model.Results
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ArtistsLocalSource @Inject constructor() {
    init { DaggerApp.roomComponent.inject(this) }

    @Inject
    lateinit var databaseRepository: ArtistDatabaseRepository

    suspend fun retrieveData(): ArrayList<Results> {

        val allArtists = databaseRepository.getData()

        return suspendCoroutine { continuation ->
            continuation.resume(allArtists)
        }
    }

    suspend fun saveData(artists: ArrayList<Results>) {
        println("saving: ${artists[0].name}")
        databaseRepository.refreshData(artists)
    }
}