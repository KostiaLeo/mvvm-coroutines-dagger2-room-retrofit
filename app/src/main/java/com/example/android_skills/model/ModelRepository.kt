package com.example.android_skills.model

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.example.android_skills.logging.TAGs
import javax.inject.Inject

// This class intended for figuring out the store we'll fetch data from (depends on internet connection)
// Furthermore this class encapsulates sources and ways for interacting with them from ViewModel.
// VM just calls method getExhibitList() and Repository independently decides way for fetching

class ModelRepository @Inject constructor(
    private val netManager: NetManager,
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource
) : ExhibitsLoader {

    override suspend fun getExhibitList(): List<Exhibit> {

        return if (netManager.isConnectedToInternet)
            retrieveRemoteData()
        else
            retrieveLocalData()

    }

    private suspend fun retrieveRemoteData(): List<Exhibit> {

        val exhibits = remoteSource.retrieveData()
        localSource.refreshData(exhibits)
        return exhibits
    }

    private suspend fun retrieveLocalData(): List<Exhibit> = localSource.retrieveData()
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
