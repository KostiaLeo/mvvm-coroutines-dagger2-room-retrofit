package com.example.android_skills.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_skills.logging.TAGs
import com.example.android_skills.dagger.daggerVM.extensions.injectRepository
import com.example.android_skills.model.model_module_description.Exhibit
import com.example.android_skills.model.ModelRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel architecture component implementation:
//      here we're gonna retrieve through viewModelScope.
//      besides it's profitably that ViewModel-class keeps alive all processes
//      during configuration changes (e.g. screen rotation)

class DaggerViewModel @Inject constructor() : ViewModel(){
    @Inject
    lateinit var modelRepository: ModelRepository

    val exhibitsList = MutableLiveData<ArrayList<Exhibit>>()

    val titleClick = MutableLiveData<Any>()

    private val tag = TAGs.viewModelTag

    @SuppressLint("CheckResult")
    fun getDataArtists() {
        injectRepository(this)

        viewModelScope.launch {
            val artists = modelRepository.getExhibitList()
            exhibitsList.postValue(ArrayList(artists))

            Log.d(tag, "ViewModel data retrieving and sharing to LiveData")
        }
    }

    fun onTitleClick() {
        titleClick.postValue(Any())
    }
}
