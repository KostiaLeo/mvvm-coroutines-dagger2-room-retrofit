package com.example.android_skills.model.remote

import android.util.Log
import com.example.android_skills.logging.TAGs
import com.example.android_skills.model.model_module_description.Exhibit
import com.example.android_skills.model.model_module_description.Exhibitions
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ExhibitsRemoteSource @Inject constructor(
    private val remoteDataSingle: Single<Exhibitions>
) {

    private val tag = TAGs.retrofitTag

    suspend fun retrieveData(): ArrayList<Exhibit> {

        return suspendCoroutine { continuation ->
            remoteDataSingle
                .subscribeOn(Schedulers.io()).retry(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { exhibitions ->
                        if(exhibitions.list.isNotEmpty())
                            continuation.resume(ArrayList(exhibitions.list))
                        else
                            continuation.resume(ArrayList(Collections.EMPTY_LIST) as ArrayList<Exhibit>)

                        Log.d(tag, "Retrofit retrieving success")
                    },
                    { error ->
                        continuation.resumeWithException(error)
                        Log.e(tag, "Retrofit retrieving failed", error)
                    }
                )
        }
    }
}