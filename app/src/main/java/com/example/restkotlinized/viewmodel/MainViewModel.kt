package com.example.restkotlinized.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.restkotlinized.model.ModelRepository
import com.example.restkotlinized.model.OnDataReadyCallback
import com.example.restkotlinized.model.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class MainViewModel(
    private val lifecycleOwner: LifecycleOwner,
    application: Application
) : AndroidViewModel(application) {
    val app = application
    val artistsList = MutableLiveData<ArrayList<Results>>()

    val selectedItem = MutableLiveData<Results>()

    @SuppressLint("CheckResult")
    fun getDataArtists() {
        ModelRepository(
            app,
            lifecycleOwner = lifecycleOwner,
            coroutineScope = viewModelScope
        ).retrieveData(object :
            OnDataReadyCallback {
            override fun onDataReady(artists: ArrayList<Results>) {
                artistsList.postValue(artists)
            }
        })
    }

// There is method for interaction between 2 fragments:
//      this one is calling in main adapter on click event
//      another fragment (subscribed on selectItem LiveData)
//      catches data and display it
    fun selectItem(artist: Results){
        selectedItem.postValue(artist)
    }

    override fun onCleared() {
        super.onCleared()
        artistsList.removeObservers(lifecycleOwner)
        selectedItem.removeObservers(lifecycleOwner)
    }
}