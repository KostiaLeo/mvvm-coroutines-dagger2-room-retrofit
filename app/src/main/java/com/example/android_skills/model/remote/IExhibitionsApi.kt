package com.example.android_skills.model.remote

import com.example.android_skills.model.model_module_description.Exhibitions
import io.reactivex.Single
import retrofit2.http.GET


interface IExhibitionsApi {
    @GET(NetworkUrl.URL_CODE)
    fun getAPIExhibitions(): Single<Exhibitions>
}