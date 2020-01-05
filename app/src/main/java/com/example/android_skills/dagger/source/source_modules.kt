package com.example.android_skills.dagger.source

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.android_skills.model.MyNewz
import com.example.android_skills.model.NetManager
import com.example.android_skills.model.remote.ArtistsRemoteSource
import com.example.android_skills.model.remote.INewsApi
import com.example.android_skills.model.remote.NetworkUrl
import com.example.android_skills.model.sqlite.ArtistDao
import com.example.android_skills.model.sqlite.ArtistDatabaseRepository
import com.example.android_skills.model.sqlite.ArtistRoomDataBase
import com.example.android_skills.model.sqlite.ArtistsLocalSource
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class SourceModule (private val application: Application){

    @Provides
    fun provideRemoteSource(): ArtistsRemoteSource = ArtistsRemoteSource()

    @Provides
    fun provideLocalSource(): ArtistsLocalSource = ArtistsLocalSource()

    @Provides
    fun provideNetManager(): NetManager = NetManager(application)
}

@Module
class RetrofitModule{

        @Provides
        @Reusable
        internal fun provideRetrofit(): Retrofit =
            Retrofit.Builder().baseUrl(NetworkUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    @Provides
    @Reusable
    internal fun provideAPI(): INewsApi = provideRetrofit().create(INewsApi::class.java)

    @Provides
    @Reusable
    fun provideNetworkDataObservable(): Single<MyNewz> = provideAPI().getAPINewz()
}

@Module
class RoomModule(val context: Context) {
    companion object {
        const val databaseName = "product_database"
    }

    @Provides
    @Reusable
    fun provideDataBase(): ArtistRoomDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            ArtistRoomDataBase::class.java,
            databaseName
        ).build()

    @Provides
    fun provideDao(): ArtistDao = provideDataBase().artistDao()

    @Provides
    fun provideLocalRepository(): ArtistDatabaseRepository = ArtistDatabaseRepository(provideDao())
}