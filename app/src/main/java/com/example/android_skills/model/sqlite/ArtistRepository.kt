package com.example.android_skills.model.sqlite

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android_skills.model.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@SuppressLint("CheckResult")
class ArtistRepository @Inject constructor(private val artistDao: ArtistDao) {

    suspend fun getData(): ArrayList<Results>{
        return suspendCoroutine {continuation ->
            artistDao.getArtists()
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

    suspend fun refreshData(artists: ArrayList<Results>) {
        println("refreshing: ${artists[0].name}")
        deleteAll()
        println("inserted ${artistDao.insert(artists).size}")
    }

    private suspend fun deleteAll(){
        println("deleted ${artistDao.deleteAllArtists()}")

    }
}