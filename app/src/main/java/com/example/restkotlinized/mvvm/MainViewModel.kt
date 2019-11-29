package com.example.restkotlinized.mvvm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.restkotlinized.model.pojo.Results

class MainViewModel() : ViewModel(){
    val model: Model = Model()
    val isLoading = ObservableField<Boolean>()
    val arstistsList = ArrayList<Results>()

    fun getDataArtists(onDataReadyCallback: OnDataReadyCallback){
        isLoading.set(true)
        model.retriveData(object : OnDataReadyCallback{
            override fun onDataReady(artists: ArrayList<Results>) {
                isLoading.set(false)
                arstistsList.addAll(artists)
                onDataReadyCallback.onDataReady(artists)
                //artists.forEach { println(it.name) }
            }
        })
    }
}