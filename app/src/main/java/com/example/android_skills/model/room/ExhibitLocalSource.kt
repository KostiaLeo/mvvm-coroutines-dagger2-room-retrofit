package com.example.android_skills.model.room

import com.example.android_skills.model.Exhibit
import com.example.android_skills.model.LocalSource
import javax.inject.Inject

// Source-class which injected to the ModelRepository for working with database

class ExhibitLocalSource @Inject constructor(
    private val databaseRepository: ExhibitDatabaseRepository
): LocalSource {

    override fun retrieveData(): List<Exhibit> {
        return databaseRepository.getData()
    }

    override fun saveData(exhibits: List<Exhibit>) {
        databaseRepository.refreshData(exhibits)
    }
}