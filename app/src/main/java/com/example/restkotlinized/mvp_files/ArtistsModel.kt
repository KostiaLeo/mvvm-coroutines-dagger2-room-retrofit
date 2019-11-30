package com.example.restkotlinized.mvp_files

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArtistsModel : MVPContract.Model {

    @SuppressLint("CheckResult")
    override fun getArtistsList(onFinishedListener: MVPContract.Model.OnFinishedListener) {
        val newsSingle = INewsApi.create().getAPINewz()
        newsSingle.subscribeOn(Schedulers.io()).retry(3)
            .observeOn(AndroidSchedulers.mainThread()).subscribe { myNews, error ->
                if (myNews != null) {
                    myNews.let {
                        onFinishedListener.onFinished(it.results.toList())
                        println("Successfully retrieved")
                    }
                } else {
                    println("Failure")
                    println(error)
                    onFinishedListener.onFailed(error)
                }
            }
    }
}