package com.example.android_skills.model.remote

import com.example.android_skills.model.Exhibitions
import retrofit2.http.GET


interface IExhibitionsApi {
    @GET(NetworkUrl.URL_CODE)
    suspend fun getAPIExhibitions(): Exhibitions
}