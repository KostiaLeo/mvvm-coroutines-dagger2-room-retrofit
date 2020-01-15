package com.example.android_skills.model.room

import android.annotation.SuppressLint
import android.util.Log
import com.example.android_skills.logging.TAGs
import com.example.android_skills.model.Exhibit
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.collections.ArrayList

// class for hidden retrieving db-data and providing it through getData() method

@SuppressLint("CheckResult")
class ExhibitDatabaseRepository @Inject constructor(
    private val exhibitDao: ExhibitDao
) {

    private val tag = TAGs.roomTag
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private val data = ArrayList<Exhibit>()

    fun getData(): List<Exhibit> {

        scope.launch {
            val exh = exhibitDao.getExhibitsAsync()
            data.addAll(exh)
        }

        if (data.size != 0) return data

        return emptyList()
    }

    fun refreshData(artists: List<Exhibit>) {

        scope.launch {
            deleteAll()
            Log.d(tag, "inserted ${exhibitDao.insert(artists).size}")
        }
    }

    private suspend fun deleteAll() {
        Log.d(tag, "deleted ${exhibitDao.deleteAllExhibits()}")
    }
}