package com.example.restkotlinized.model.sqlite

import androidx.lifecycle.*
import com.example.restkotlinized.model.Results

class ArtistsLocalSource(
    private val liveDataProvider: LiveDataProvider,
    private val owner: LifecycleOwner
)  : LifecycleObserver {

    fun retrieveData(onDataReadyCallback: OnDataLocalReadyCallback) {
        liveDataProvider.allArtists.observe(owner, Observer { artists ->
            onDataReadyCallback.onLocalDataReady(ArrayList(artists))
        })
    }

    fun saveData(artists: ArrayList<Results>) {
        liveDataProvider.insert(artists)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun dispose(){
        liveDataProvider.allArtists.removeObservers(owner)
    }
}

interface OnDataLocalReadyCallback {
    fun onLocalDataReady(artists: ArrayList<Results>)
}