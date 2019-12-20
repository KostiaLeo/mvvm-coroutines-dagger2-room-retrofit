package com.example.restkotlinized.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.restkotlinized.model.ModelRepository
import com.example.restkotlinized.model.Results
import kotlinx.coroutines.launch

class MainViewModel(application: Application
) : AndroidViewModel(application) {

    private val app = application
    val artistsList = MutableLiveData<ArrayList<Results>>()

    val selectedItem = MutableLiveData<Results>()
    val titleClick = MutableLiveData<Any>()

    @SuppressLint("CheckResult")
    fun getDataArtists() {
        viewModelScope.launch {
            artistsList.postValue(
                ModelRepository(app)
                    .retrieveData()
            )
        }
    }

    fun selectItem(artist: Results) {
        selectedItem.postValue(artist)
    }

    fun onTitleClick() {
        titleClick.postValue(Any())
    }
}