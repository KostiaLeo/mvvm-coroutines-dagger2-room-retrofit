package com.example.android_skills.model.remote

import com.example.android_skills.model.MyNewz
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface INewsApi {
    @GET(NetworkUrl.URL_CODE)
    fun getAPINewz(): Single<MyNewz>
}