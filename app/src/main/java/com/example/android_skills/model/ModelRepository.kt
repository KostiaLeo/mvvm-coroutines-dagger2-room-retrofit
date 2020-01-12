package com.example.android_skills.model

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.model.model_module_description.Exhibit
import com.example.android_skills.model.model_module_description.ExhibitsLoader
import com.example.android_skills.model.remote.ExhibitsRemoteSource
import com.example.android_skills.model.room.ExhibitLocalSource
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// This class intended for figuring out the store we'll fetch data from (depends on internet connection)
// Furthermore this class incapsulates sources and ways for interacting with them from ViewModel
// VM just calls method getExhibitList() and Repository independently decides way for fethcing

class ModelRepository @Inject constructor() : ExhibitsLoader {

    init { DaggerApp.sourceComponent.inject(this) }

    @Inject
    lateinit var netManager: NetManager
    @Inject
    lateinit var localSource: ExhibitLocalSource
    @Inject
    lateinit var remoteSource: ExhibitsRemoteSource


    override suspend fun getExhibitList(): List<Exhibit> {
        val artists =
            if(netManager.isConnectedToInternet)
                retrieveRemoteData()
            else
                retrieveLocalData()

        return suspendCoroutine {
            it.resume(artists)
        }
    }

    private suspend fun retrieveRemoteData() : ArrayList<Exhibit> {
        val exhibits = remoteSource.retrieveData()
        localSource.saveData(exhibits)
        return suspendCoroutine {
            it.resume(exhibits)
        }
    }

    private suspend fun retrieveLocalData(): ArrayList<Exhibit> {
        val exhibits = localSource.retrieveData()

        return suspendCoroutine {
            it.resume(exhibits)
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
