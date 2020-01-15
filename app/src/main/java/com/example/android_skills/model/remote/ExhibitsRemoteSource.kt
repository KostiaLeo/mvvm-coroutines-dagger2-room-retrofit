package com.example.android_skills.model.remote

import android.util.Log
import com.example.android_skills.logging.TAGs
import com.example.android_skills.model.Exhibit
import com.example.android_skills.model.RemoteSource
import kotlinx.coroutines.*
import javax.inject.Inject

class ExhibitsRemoteSource @Inject constructor(
    private val api: IExhibitionsApi
) : RemoteSource {

    override fun retrieveData(): List<Exhibit> = runBlocking {
        val list = api.getAPIExhibitions().list
        Log.d(TAGs.retrofitTag, "Retrieved ${list.size} items from network")
        return@runBlocking list
    }
}