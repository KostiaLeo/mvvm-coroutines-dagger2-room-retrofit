package com.example.restkotlinized.model.remote

import android.annotation.SuppressLint
import com.example.restkotlinized.model.pojo.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArtistsRemoteSource {
    @SuppressLint("CheckResult")
    fun retrieveData(onDataReadyCallback: OnDataRemoteReadyCallback) {
        val artistsSingle = INewsApi.create().getAPINewz()

        artistsSingle
            .subscribeOn(Schedulers.io())
            .retry(3)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    onDataReadyCallback.onRemoteDataReady(ArrayList(it.results.toList()))
                    println("Successfully retrieved")
                },
                {
                    println(it)
                }
            )
    }
}

interface OnDataRemoteReadyCallback {
    fun onRemoteDataReady(films: ArrayList<Result>)
}