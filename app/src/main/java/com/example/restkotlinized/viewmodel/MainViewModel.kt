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
    private val lifecycleOwner: LifecycleOwner
) : ViewModel() {
    private lateinit var localModelRepository: ModelRepository

    val artistsList = MutableLiveData<ArrayList<Results>>()


    @SuppressLint("CheckResult")
    fun getDataArtists(context: Context) {
        localModelRepository = ModelRepository(
            context,
            lifecycleOwner = lifecycleOwner,
            coroutineScope = viewModelScope
        )

        localModelRepository.retrieveData(object :
            OnDataReadyCallback {
            override fun onDataReady(artists: ArrayList<Results>) {
                artistsList.value = artists
            }
        })
    }
}