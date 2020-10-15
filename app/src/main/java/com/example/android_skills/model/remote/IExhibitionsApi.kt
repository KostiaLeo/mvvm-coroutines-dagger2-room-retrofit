package com.example.android_skills.model.remote

import com.example.android_skills.model.Item
import com.example.android_skills.model.ItemResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface IExhibitionsApi {
    @GET("/")
    suspend fun getAPIExhibitions(
        @Query("page") page: Int
    ): List<Item>
}