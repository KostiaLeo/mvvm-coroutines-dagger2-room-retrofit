package com.example.android_skills.model.room

import android.annotation.SuppressLint
import com.example.android_skills.model.Exhibit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// class for hidden retrieving db-data and providing it through getData() method

@SuppressLint("CheckResult")
class ExhibitDatabaseRepository @Inject constructor(
    private val exhibitDao: ExhibitDao
) {

    suspend fun getData(): List<Exhibit> {
        val exh = exhibitDao.getExhibitsAsync()
        return suspendCoroutine {
            if (exh.isNotEmpty()) it.resume(exh)
            else it.resume(emptyList())
        }
    }

    suspend fun refreshData(artists: List<Exhibit>) {
        deleteAll()
        exhibitDao.insert(artists).size
    }

    private suspend fun deleteAll() {
        exhibitDao.deleteAllExhibits()
    }
}