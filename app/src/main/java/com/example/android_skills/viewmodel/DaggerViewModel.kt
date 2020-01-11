package com.example.android_skills.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_skills.dagger.daggerVM.extensions.injectRepository
import com.example.android_skills.model.model_module_description.Exhibit
import com.example.android_skills.model.ModelRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DaggerViewModel @Inject constructor() : ViewModel(){
    @Inject
    lateinit var modelRepository: ModelRepository

    val exhibitsList = MutableLiveData<ArrayList<Exhibit>>()

    val titleClick = MutableLiveData<Any>()

    @SuppressLint("CheckResult")
    fun getDataArtists() {
        injectRepository(this)

        viewModelScope.launch {
            val artists = modelRepository.getExhibitList()

            exhibitsList.postValue(ArrayList(artists))
        }
    }

    fun onTitleClick() {
        titleClick.postValue(Any())
    }
}