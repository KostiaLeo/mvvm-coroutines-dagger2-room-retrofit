package com.example.android_skills.model.remote

import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.model.model_module_description.Exhibit
import com.example.android_skills.model.model_module_description.Exhibitions
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ExhibitsRemoteSource @Inject constructor() {

    @Inject
    lateinit var remoteDataSingle: Single<Exhibitions>

    suspend fun retrieveData(): ArrayList<Exhibit> {
        DaggerApp.retrofitComponent.inject(this)

        return suspendCoroutine {
            remoteDataSingle
                .subscribeOn(Schedulers.io()).retry(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { exhibitions -> it.resume(ArrayList(exhibitions.list)) },
                    { error -> it.resumeWithException(error) }
                )
        }
    }
}