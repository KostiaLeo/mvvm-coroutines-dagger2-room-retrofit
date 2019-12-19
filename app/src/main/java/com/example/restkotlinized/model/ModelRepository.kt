package com.example.restkotlinized.model

import com.example.restkotlinized.model.remote.ArtistsRemoteSource
import com.example.restkotlinized.model.remote.OnDataRemoteReadyCallback
import com.example.restkotlinized.model.pojo.Result

class ModelRepository{
    private val remoteSource = ArtistsRemoteSource()

    fun retrieveData(onDataReadyCallback: OnDataReadyCallback) {
        remoteSource.retrieveData(object : OnDataRemoteReadyCallback {
            override fun onRemoteDataReady(films: ArrayList<Result>) {
                onDataReadyCallback.onDataReady(films)
            }
        })
    }
}

interface OnDataReadyCallback {
    fun onDataReady(films: ArrayList<Result>)
}