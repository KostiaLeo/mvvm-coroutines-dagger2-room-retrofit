package com.example.android_skills.model.room

import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.model.model_module_description.Exhibit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ExhibitLocalSource @Inject constructor() {
    init { DaggerApp.roomComponent.inject(this) }
    @Inject
    lateinit var databaseRepository: ExhibitDatabaseRepository

    suspend fun retrieveData(): ArrayList<Exhibit> {

        val allExhibits = databaseRepository.getData()

        return suspendCoroutine { continuation ->
            continuation.resume(allExhibits)
        }
    }

    suspend fun saveData(exhibits: ArrayList<Exhibit>) {
        databaseRepository.refreshData(exhibits)
    }
}