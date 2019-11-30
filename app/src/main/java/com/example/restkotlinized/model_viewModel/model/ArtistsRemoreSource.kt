package com.example.restkotlinized.model_viewModel.model

import android.annotation.SuppressLint
import com.example.restkotlinized.mvp_files.INewsApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArtistsRemoreSource {
    @SuppressLint("CheckResult")
    fun retrieveData(onDataReadyCallback: OnDataRemoteReadyCallback) {
        val newsSingle = INewsApi.create().getAPINewz()
        newsSingle.subscribeOn(Schedulers.io()).retry(3)
            .observeOn(AndroidSchedulers.mainThread()).subscribe { myNews, error ->
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
interface OnDataRemoteReadyCallback{
    fun onRemoteDataReady(artists: ArrayList<Results>)
}