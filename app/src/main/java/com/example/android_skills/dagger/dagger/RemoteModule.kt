package com.example.android_skills.dagger.dagger

import com.example.android_skills.model.model_module_description.Exhibitions
import com.example.android_skills.model.remote.ExhibitsRemoteSource
import com.example.android_skills.model.remote.IExhibitionsApi
import com.example.android_skills.model.remote.NetworkUrl
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule{

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(NetworkUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    internal fun provideAPI(retrofit: Retrofit): IExhibitionsApi = retrofit.create(IExhibitionsApi::class.java)

    @Provides
    @Singleton
    fun provideNetworkDataObservable(api: IExhibitionsApi): Single<Exhibitions> = api.getAPIExhibitions()

    @Provides
    @Singleton
    fun provideRemoteSource(single: Single<Exhibitions>): ExhibitsRemoteSource = ExhibitsRemoteSource(single)
}
