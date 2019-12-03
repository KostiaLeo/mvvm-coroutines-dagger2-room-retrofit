package com.example.restkotlinized.model.remote

import android.annotation.SuppressLint
import com.example.restkotlinized.model.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArtistsRemoteSource {
    @SuppressLint("CheckResult")
    fun retrieveData(onDataReadyCallback: OnDataRemoteReadyCallback) {
        val artistsSingle = INewsApi.create().getAPINewz()
        artistsSingle.subscribeOn(Schedulers.io()).retry(3)
            .observeOn(AndroidSchedulers.mainThread()).subscribe { myNews, error ->
                println(myNews == null)
                if (myNews != null) {
                    myNews.let {
                        onDataReadyCallback.onRemoteDataReady(ArrayList(it.results.toList()))
                        println("Successfully retrieved")
                    }
                } else {
                    println("Failure")
                    println(error)
                }
            }
    }
}

interface OnDataRemoteReadyCallback {
    fun onRemoteDataReady(artists: ArrayList<Results>)
}