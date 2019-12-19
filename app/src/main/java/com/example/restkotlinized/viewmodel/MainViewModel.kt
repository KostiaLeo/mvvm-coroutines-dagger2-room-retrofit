package com.example.restkotlinized.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.example.restkotlinized.model.ModelRepository
import com.example.restkotlinized.model.OnDataReadyCallback
import com.example.restkotlinized.model.pojo.Result

class MainViewModel(
    private val lifecycleOwner: LifecycleOwner
) : ViewModel() {

    val filmsList = MutableLiveData<ArrayList<Result>>()

    val selectedItem = MutableLiveData<Result>()
    val titleClick = MutableLiveData<Any>()
    val isLoaded = MutableLiveData(false)

    @SuppressLint("CheckResult")
    fun getDataArtists() {
        ModelRepository()
            .retrieveData(object :
                OnDataReadyCallback {
                override fun onDataReady(films: ArrayList<Result>) {
                    filmsList.postValue(films)
                    isLoaded.postValue(true)
                }
            })
    }

    fun selectItem(film: Result) {
        selectedItem.postValue(film)
    }

    fun onTitleClick() {
        titleClick.postValue(Any())
    }

    override fun onCleared() {
        super.onCleared()
        filmsList.removeObservers(lifecycleOwner)
        selectedItem.removeObservers(lifecycleOwner)
    }
}