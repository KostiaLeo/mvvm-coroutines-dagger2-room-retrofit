package com.example.restkotlinized.model.sqlite

import androidx.lifecycle.*
import com.example.restkotlinized.model.Results

class ArtistsLocalSource(
    private val liveDataProvider: LiveDataProvider,
    private val owner: LifecycleOwner
) {

    fun retrieveData(onDataReadyCallback: OnDataLocalReadyCallback) {
        liveDataProvider.allArtists.observe(owner, Observer { artists ->
            onDataReadyCallback.onLocalDataReady(ArrayList(artists))
        })
    }

    fun saveData(artists: ArrayList<Results>) {
        liveDataProvider.insert(artists)
    }
}

interface OnDataLocalReadyCallback {
    fun onLocalDataReady(artists: ArrayList<Results>)
}