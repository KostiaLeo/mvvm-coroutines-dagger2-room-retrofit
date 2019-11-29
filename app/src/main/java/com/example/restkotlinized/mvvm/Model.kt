package com.example.restkotlinized.mvvm

import android.annotation.SuppressLint
import com.example.restkotlinized.model.INewsApi
import com.example.restkotlinized.model.pojo.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class Model {
    @SuppressLint("CheckResult")
    fun retriveData(onDataReadyCallback: OnDataReadyCallback) {
        val newsSingle = INewsApi.create().getAPINewz()
        newsSingle.subscribeOn(Schedulers.io()).retry(3)
            .observeOn(AndroidSchedulers.mainThread()).subscribe { myNews, error ->
                if (myNews != null) {
                    myNews.let {
                        onDataReadyCallback.onDataReady(ArrayList(it.results.toList()))
                        println("Successfully retrieved")
                    }
                } else {
                    println("Failure")
                    println(error)
                }
            }
    }
}
interface OnDataReadyCallback{
    fun onDataReady(artists: ArrayList<Results>)
}