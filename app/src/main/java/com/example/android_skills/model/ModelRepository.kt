package com.example.android_skills.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.model.remote.ArtistsRemoteSource
import com.example.android_skills.model.sqlite.ArtistsLocalSource
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ModelRepository @Inject constructor() {
    init { DaggerApp.sourceComponent.inject(this) }

    @Inject
    lateinit var netManager: NetManager
    @Inject
    lateinit var localSource: ArtistsLocalSource
    @Inject
    lateinit var remoteSource: ArtistsRemoteSource


    suspend fun retrieveData() : ArrayList<Results> {
        val artists =
            if(netManager.isConnectedToInternet)
                retrieveRemoteData()
            else
                retrieveLocalData()

        return suspendCoroutine {
            it.resume(artists)
        }
    }

    private suspend fun retrieveRemoteData() : ArrayList<Results> {
        val artists = remoteSource.retrieveData()
        localSource.saveData(artists)
        return suspendCoroutine {
            it.resume(artists)
        }
    }

    private suspend fun retrieveLocalData(): ArrayList<Results> {
        val artists = localSource.retrieveData()

        return suspendCoroutine {
            it.resume(artists)
        }
    }
}

class NetManager @Inject constructor(private val applicationContext: Context) {

    val isConnectedToInternet: Boolean
        @SuppressLint("MissingPermission")
        get() {
            val conManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = conManager.activeNetworkInfo
            return network != null && network.isConnected
        }
}