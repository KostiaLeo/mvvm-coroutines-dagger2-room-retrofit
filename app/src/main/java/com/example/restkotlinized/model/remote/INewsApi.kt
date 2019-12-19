package com.example.restkotlinized.model.remote

import com.example.restkotlinized.model.pojo.Films
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface INewsApi {
    @GET("popular?api_key=e592fbac94840ecb8d5ff29fe14ed33b&language=en-US&page=1")
    fun getAPINewz(): Single<Films>
    companion object Factory {
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        fun create(): INewsApi {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(INewsApi::class.java)
        }
    }
}