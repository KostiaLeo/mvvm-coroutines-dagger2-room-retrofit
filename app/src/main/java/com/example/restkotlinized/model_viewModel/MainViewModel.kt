package com.example.restkotlinized.model_viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.restkotlinized.model.pojo.Results

class MainViewModel : ViewModel(){
    val model: Model = Model()
    val isLoading = ObservableField<Boolean>()
    val artistsList = ArrayList<Results>()

    fun getDataArtists(onDataReadyCallback: OnDataReadyCallback){
        isLoading.set(true)
        model.retriveData(object : OnDataReadyCallback{
            override fun onDataReady(artists: ArrayList<Results>) {
                isLoading.set(false)
                artistsList.addAll(artists)
                onDataReadyCallback.onDataReady(artists)
            }
        })
    }
}