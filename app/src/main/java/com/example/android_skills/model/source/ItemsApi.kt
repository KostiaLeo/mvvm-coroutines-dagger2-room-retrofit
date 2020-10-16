package com.example.android_skills.model.source

import com.example.android_skills.model.data.Item
import retrofit2.http.GET
import retrofit2.http.Query


interface ItemsApi {
    @GET("/")
    suspend fun getAPIExhibitions(
        @Query("page") page: Int
    ): List<Item>
}