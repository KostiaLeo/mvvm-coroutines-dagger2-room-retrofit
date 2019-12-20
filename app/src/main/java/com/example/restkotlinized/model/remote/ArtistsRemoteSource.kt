package com.example.restkotlinized.model.remote

import com.example.restkotlinized.model.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ArtistsRemoteSource {
    suspend fun retrieveData(): ArrayList<Results> = suspendCoroutine {
        INewsApi.create().getAPINewz()
            .subscribeOn(Schedulers.io()).retry(3)
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { news -> it.resume(ArrayList(news.results.toList())) },
                { error -> println(error) }
            )
    }
}