package com.example.android_skills.model.remote

import android.util.Log
import com.example.android_skills.logging.TAGs
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

    private val tag = TAGs.retrofitTag

    suspend fun retrieveData(): ArrayList<Exhibit> {
        DaggerApp.retrofitComponent.inject(this)

        return suspendCoroutine {
            remoteDataSingle
                .subscribeOn(Schedulers.io()).retry(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { exhibitions ->
                        it.resume(ArrayList(exhibitions.list))
                        Log.d(tag, "Retrofit retrieving success")
                    },
                    { error ->
                        it.resumeWithException(error)
                        Log.e(tag, "Retrofit retrieving failed", error)
                    }
                )
        }
    }
}