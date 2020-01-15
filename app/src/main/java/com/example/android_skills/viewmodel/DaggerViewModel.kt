package com.example.android_skills.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_skills.logging.TAGs
import com.example.android_skills.model.Exhibit
import com.example.android_skills.model.ExhibitsLoader
import javax.inject.Inject

// ViewModel architecture component implementation:
//      here we're gonna retrieve data through viewModelScope.
//      besides VM usage is profitably since ViewModel-class keeps alive and saves all processes
//      during configuration changes (e.g. screen rotation)

class DaggerViewModel @Inject constructor(
    private val loader: ExhibitsLoader
) : ViewModel(){
    private val _exhibitsListMutable = MutableLiveData<ArrayList<Exhibit>>()
    val titleClick = MutableLiveData<Any>()

    init {
        loadData()
    }

    private val tag = TAGs.viewModelTag

    @SuppressLint("CheckResult")
    private fun loadData() {
            val artists = loader.getExhibitList()
            _exhibitsListMutable.postValue(ArrayList(artists))

            Log.d(tag, "ViewModel data retrieving and sharing to LiveData")
    }

    fun getExhibitsList(): LiveData<ArrayList<Exhibit>> = _exhibitsListMutable
}
