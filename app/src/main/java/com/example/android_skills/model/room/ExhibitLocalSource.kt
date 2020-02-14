package com.example.android_skills.model.room

import android.util.Log
import com.example.android_skills.logging.TAGs
import com.example.android_skills.model.Exhibit
import com.example.android_skills.model.LocalSource
import kotlinx.coroutines.*
import javax.inject.Inject

// Source-class which injected to the ModelRepository for working with database

class ExhibitLocalSource @Inject constructor(
    private val exhibitDao: ExhibitDao
) : LocalSource {

    override suspend fun retrieveData(): List<Exhibit> = withContext(Dispatchers.IO){
        val exhibits = exhibitDao.getExhibits()

        Log.d(TAGs.roomTag, "Retrieved ${exhibits.size} items from database")

        if (exhibits.isNotEmpty()) exhibits
        else emptyList()
    }

    override suspend fun refreshData(exhibits: List<Exhibit>) {
        exhibitDao.insert(exhibits).size
    }
}