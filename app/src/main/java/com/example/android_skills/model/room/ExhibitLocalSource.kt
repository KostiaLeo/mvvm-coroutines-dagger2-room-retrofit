package com.example.android_skills.model.room

import android.util.Log
import com.example.android_skills.logging.TAGs
import com.example.android_skills.model.Exhibit
import com.example.android_skills.model.LocalSource
import kotlinx.coroutines.*
import javax.inject.Inject

// Source-class which injected to the ModelRepository for working with database

class ExhibitLocalSource @Inject constructor(
    private val databaseRepository: ExhibitDatabaseRepository
) : LocalSource {
    val a = ArrayList<Exhibit>()

    override fun retrieveData(): List<Exhibit> {
        return runBlocking {
            val list = databaseRepository.getData()
            Log.d(TAGs.roomTag, "Retrieved ${list.size} items from database")
            return@runBlocking list
        }
    }

    override fun saveData(exhibits: List<Exhibit>) {
        CoroutineScope(Dispatchers.IO + Job()).launch {
            databaseRepository.refreshData(exhibits)
        }
    }
}