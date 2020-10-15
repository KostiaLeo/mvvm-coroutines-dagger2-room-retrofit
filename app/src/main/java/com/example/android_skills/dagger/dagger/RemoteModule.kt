package com.example.android_skills.dagger.dagger

import com.example.android_skills.model.remote.BASE_URL
import com.example.android_skills.model.remote.IExhibitionsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    internal fun provideAPI(retrofit: Retrofit): IExhibitionsApi = retrofit.create(IExhibitionsApi::class.java)
}
