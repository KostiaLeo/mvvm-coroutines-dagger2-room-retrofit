package com.example.android_skills.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_skills.dagger.daggerVM.extensions.injectRepository
import com.example.android_skills.model.ModelRepository
import com.example.android_skills.model.Results
import kotlinx.coroutines.launch
import javax.inject.Inject

class DaggerViewModel @Inject constructor() : ViewModel(){
    @Inject
    lateinit var modelRepository: ModelRepository
    
    val artistsList = MutableLiveData<ArrayList<Results>>()

    val selectedItem = MutableLiveData<Results>()
    val titleClick = MutableLiveData<Any>()

    @SuppressLint("CheckResult")
    fun getDataArtists() {
        injectRepository(this)
        
        viewModelScope.launch {
            val artists = modelRepository.retrieveData()

            artistsList.postValue(artists)
        }
    }

    fun selectItem(artist: Results) {
        selectedItem.postValue(artist)
    }

    fun onTitleClick() {
        titleClick.postValue(Any())
    }
}