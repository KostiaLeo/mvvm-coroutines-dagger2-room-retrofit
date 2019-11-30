package com.example.restkotlinized.model_viewModel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.restkotlinized.model_viewModel.model.ModelRepository
import com.example.restkotlinized.model_viewModel.model.OnDataReadyCallback
import com.example.restkotlinized.model_viewModel.model.Results

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val model: ModelRepository =
        ModelRepository(application)

    val isLoading = ObservableField<Boolean>()
    val artistsList = MutableLiveData<ArrayList<Results>>()

    fun getDataArtists(){
        isLoading.set(true)
        model.retrieveData(object :
            OnDataReadyCallback {
            override fun onDataReady(artists: ArrayList<Results>) {
                isLoading.set(false)
                artistsList.value = artists
            }
        })
    }
}