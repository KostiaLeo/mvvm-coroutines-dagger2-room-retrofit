package com.example.android_skills.model.room

import com.example.android_skills.model.model_module_description.Exhibit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// Source-class which injected to the ModelRepository for working with database

class ExhibitLocalSource @Inject constructor(
    private val databaseRepository: ExhibitDatabaseRepository
) {

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