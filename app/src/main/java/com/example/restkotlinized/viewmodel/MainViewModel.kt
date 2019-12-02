package com.example.restkotlinized.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.restkotlinized.model.ModelRepository
import com.example.restkotlinized.model.OnDataReadyCallback
import com.example.restkotlinized.model.remote.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var localModelRepository: ModelRepository
    private lateinit var viewModelStoreOwner: ViewModelStoreOwner
    private lateinit var lifecycleOwner: LifecycleOwner
    private val context = application

    private val loadSubject = BehaviorSubject.create<Any>()
    private val loadObservable =
        loadSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    val artistsList = MutableLiveData<ArrayList<Results>>()
    val isLoading = MutableLiveData(true)

    fun getData(viewModelStoreOwner: ViewModelStoreOwner, lifecycleOwner: LifecycleOwner) {
        this.viewModelStoreOwner = viewModelStoreOwner
        this.lifecycleOwner = lifecycleOwner
        loadSubject.onNext(Any())
        getDataArtists()
    }

    @SuppressLint("CheckResult")
    private fun getDataArtists() {
        loadObservable.subscribe {
            localModelRepository = ModelRepository(
                context,
                viewModelStoreOwner = viewModelStoreOwner,
                lifecycleOwner = lifecycleOwner
            )

            localModelRepository.retrieveData(object :
                OnDataReadyCallback {
                override fun onDataReady(artists: ArrayList<Results>) {
                    isLoading.postValue(false)
                    artistsList.value = artists
                }
            })
        }
    }
}