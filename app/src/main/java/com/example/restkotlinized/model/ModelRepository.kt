package com.example.restkotlinized.model

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LifecycleOwner
import com.example.restkotlinized.model.remote.ArtistsRemoteSource
import com.example.restkotlinized.model.remote.OnDataRemoteReadyCallback
import com.example.restkotlinized.model.sqlite.ArtistsLocalSource
import com.example.restkotlinized.model.sqlite.OnDataLocalReadyCallback
import com.example.restkotlinized.model.sqlite.LiveDataProvider
import kotlinx.coroutines.CoroutineScope

class ModelRepository(
    private val applicationContext: Context,
    val lifecycleOwner: LifecycleOwner,
    val coroutineScope: CoroutineScope
) {
    private val liveDataProvider: LiveDataProvider = LiveDataProvider(applicationContext, coroutineScope)
    private var localSource: ArtistsLocalSource

    private val remoteSource = ArtistsRemoteSource()

    private lateinit var onDataReadyCallback: OnDataReadyCallback

    init {
        localSource = ArtistsLocalSource(liveDataProvider, lifecycleOwner)
    }

    fun retrieveData(onDataReadyCallback: OnDataReadyCallback) {
        this.onDataReadyCallback = onDataReadyCallback

        NetManager(applicationContext).isConnectedToInternet?.let {
            if (it) {
                retrieveRemoteData()
            } else {
                retrieveLocalData()
            }
        }
    }

    private fun retrieveRemoteData(){
        remoteSource.retrieveData(object : OnDataRemoteReadyCallback {
            override fun onRemoteDataReady(artists: ArrayList<Results>) {
                onDataReadyCallback.onDataReady(artists)
                localSource.saveData(artists)
            }
        })
    }

    private fun retrieveLocalData(){
        localSource.retrieveData(object : OnDataLocalReadyCallback {
            override fun onLocalDataReady(artists: ArrayList<Results>) {
                onDataReadyCallback.onDataReady(artists)
            }
        })
    }
}

interface OnDataReadyCallback {
    fun onDataReady(artists: ArrayList<Results>)
}

private class NetManager(private val applicationContext: Context) {

    val isConnectedToInternet: Boolean?
        @SuppressLint("MissingPermission")
        get() {
            val conManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = conManager.activeNetworkInfo
            return network != null && network.isConnectedOrConnecting
        }
}