package com.example.android_skills.dagger.dagger

import com.example.android_skills.model.RemoteSource
import com.example.android_skills.model.remote.ExhibitsRemoteSource
import com.example.android_skills.model.remote.IExhibitionsApi
import com.example.android_skills.model.remote.NetworkUrl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [RemoteModule::class])
abstract class RemoteSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteSource(exhibitsRemoteSource: ExhibitsRemoteSource): RemoteSource
}

@Module
class RemoteModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(NetworkUrl.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    internal fun provideAPI(retrofit: Retrofit): IExhibitionsApi = retrofit.create(IExhibitionsApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteSource(api: IExhibitionsApi): ExhibitsRemoteSource = ExhibitsRemoteSource(api)
}
