package com.example.android_skills.model.room

import android.annotation.SuppressLint
import android.util.Log
import com.example.android_skills.model.model_module_description.Exhibit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@SuppressLint("CheckResult")
class ExhibitDatabaseRepository @Inject constructor(private val exhibitDao: ExhibitDao) {

    suspend fun getData(): ArrayList<Exhibit>{
        return suspendCoroutine {continuation ->
            exhibitDao.getArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { continuation.resume(ArrayList(it)) },
                    {
                        Log.d("Room retrieving failed", "$it")
                        continuation.resumeWithException(it)
                    }
                )
        }
    }

    suspend fun refreshData(artists: ArrayList<Exhibit>) {
        deleteAll()
        println("inserted ${exhibitDao.insert(artists).size}")
    }

    private suspend fun deleteAll(){
        println("deleted ${exhibitDao.deleteAllArtists()}")
    }
}