package com.example.restkotlinized.model

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

interface INewsApi {
    @GET("api/v1.0/products/")
    fun getAPINewz(@Query("format") format: String): Call<MyNewz>

    companion object Factory {
        private val BASE_URL = "http://allcom.lampawork.com/"
        fun create(): INewsApi {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(INewsApi::class.java)
        }
    }
}