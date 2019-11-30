package com.example.restkotlinized.model_viewModel.model

import android.content.Context
import android.net.ConnectivityManager
import kotlin.collections.ArrayList

class ModelRepository(applicationContext: Context) {
    private val remoteSource =
        ArtistsRemoreSource()
    private val localSource = ArtistsLocalSource()

    private val netManager =
        NetManager(applicationContext)


    fun retrieveData(onDataReadyCallback: OnDataReadyCallback) {
        netManager.isconnectedToInternet?.let {
            if(it) {
                remoteSource.retrieveData(object :
                    OnDataRemoteReadyCallback {
                    override fun onRemoteDataReady(artists: ArrayList<Results>) {
                        localSource.saveRepositories(artists)
                        onDataReadyCallback.onDataReady(artists)
                    }
                })
            } else {
                localSource.retrieveData(object :
                    OnDataLocalReadyCallback {
                    override fun onLocalDataReady(artists: ArrayList<Results>) {
                        onDataReadyCallback.onDataReady(artists)
                    }
                })
            }
        }
    }
}

interface OnDataReadyCallback{
    fun onDataReady(artists: ArrayList<Results>)
}

private class NetManager(private val applicationContext: Context){
    val isconnectedToInternet: Boolean?
        get() {
            val conManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = conManager.activeNetworkInfo
            return network != null && network.isConnected
        }
}