package com.example.restkotlinized.model.sqlite

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.restkotlinized.model.Results

class ArtistsLocalSource(
    private val artistsViewModel: ArtistViewModel,
    private val owner: LifecycleOwner
) {

    fun retrieveData(onDataReadyCallback: OnDataLocalReadyCallback) {
        artistsViewModel.allArtists.observe(owner, Observer { artists ->
            onDataReadyCallback.onLocalDataReady(ArrayList(artists))
        })
    }

    fun saveData(artists: ArrayList<Results>) {
        artists.forEach { artistsViewModel.insert(it) }
    }
}

interface OnDataLocalReadyCallback {
    fun onLocalDataReady(artists: ArrayList<Results>)
}