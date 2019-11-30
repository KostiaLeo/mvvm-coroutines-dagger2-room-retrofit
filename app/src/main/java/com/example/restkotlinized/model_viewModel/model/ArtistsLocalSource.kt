package com.example.restkotlinized.model_viewModel.model

import android.annotation.SuppressLint
import com.example.restkotlinized.mvp_files.INewsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArtistsLocalSource {

    @SuppressLint("CheckResult")
    fun retrieveData(onDataReadyCallback: OnDataLocalReadyCallback) {
        val newsSingle = INewsApi.create().getAPINewz()
        newsSingle.subscribeOn(Schedulers.io()).retry(3)
            .observeOn(AndroidSchedulers.mainThread()).subscribe { myNews, error ->
                if (myNews != null) {
                    myNews.let {
                        onDataReadyCallback.onLocalDataReady(ArrayList(it.results.toList()))
                        println("Successfully retrieved")
                    }
                } else {
                    println("Failure")
                    println(error)
                }
            }
    }

    fun saveRepositories(artists: ArrayList<Results>){

    }
}

interface OnDataLocalReadyCallback{
    fun onLocalDataReady(artists: ArrayList<Results>)
}