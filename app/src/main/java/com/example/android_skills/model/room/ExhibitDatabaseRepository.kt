package com.example.android_skills.model.room

import android.annotation.SuppressLint
import android.util.Log
import com.example.android_skills.logging.TAGs
import com.example.android_skills.model.model_module_description.Exhibit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// class for hidden retrieving db-data and providing it through getData() method

@SuppressLint("CheckResult")
class ExhibitDatabaseRepository @Inject constructor(private val exhibitDao: ExhibitDao) {

    private val tag = TAGs.roomTag

    suspend fun getData(): ArrayList<Exhibit>{
        return suspendCoroutine {continuation ->
            exhibitDao.getArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if(it.isNotEmpty())
                            continuation.resume(ArrayList(it))
                        else
                            continuation.resume(ArrayList(Collections.EMPTY_LIST) as ArrayList<Exhibit>)
                        Log.d(tag , "Room retrieving success")
                    },
                    {
                        Log.e(tag,"Room retrieving failed", it)
                        continuation.resumeWithException(it)
                    }
                )
        }
    }

    suspend fun refreshData(artists: ArrayList<Exhibit>) {
        deleteAll()
        Log.d(tag,"inserted ${exhibitDao.insert(artists).size}")
    }

    private suspend fun deleteAll(){
        Log.d(tag,"deleted ${exhibitDao.deleteAllArtists()}")
    }
}