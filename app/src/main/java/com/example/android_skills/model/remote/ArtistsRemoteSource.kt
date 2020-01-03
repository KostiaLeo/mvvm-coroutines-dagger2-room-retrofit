package com.example.android_skills.model.remote

import com.example.android_skills.model.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ArtistsRemoteSource @Inject constructor() {
    suspend fun retrieveData(): ArrayList<Results> = suspendCoroutine {
        INewsApi.create().getAPINewz()
            .subscribeOn(Schedulers.io()).retry(3)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { news -> it.resume(news.results.toList() as ArrayList<Results>) },
                { error -> println(error) }
            )
    }
}