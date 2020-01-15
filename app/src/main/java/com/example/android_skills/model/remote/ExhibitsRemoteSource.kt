package com.example.android_skills.model.remote

import com.example.android_skills.model.Exhibit
import com.example.android_skills.model.RemoteSource
import kotlinx.coroutines.*
import javax.inject.Inject

class ExhibitsRemoteSource @Inject constructor(
    private val api: IExhibitionsApi
) : RemoteSource {

    override fun retrieveData(): List<Exhibit> = runBlocking {
        api.getAPIExhibitions().list
    }
}