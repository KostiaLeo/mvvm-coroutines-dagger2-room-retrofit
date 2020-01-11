package com.example.android_skills.dagger.source

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.android_skills.model.model_module_description.Exhibitions
import com.example.android_skills.model.NetManager
import com.example.android_skills.model.remote.ExhibitsRemoteSource
import com.example.android_skills.model.remote.IExhibitionsApi
import com.example.android_skills.model.remote.NetworkUrl
import com.example.android_skills.model.room.ExhibitDao
import com.example.android_skills.model.room.ExhibitDatabaseRepository
import com.example.android_skills.model.room.ExhibitRoomDataBase
import com.example.android_skills.model.room.ExhibitLocalSource
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//@Qualifier
//@Retention(AnnotationRetention.RUNTIME)
//annotation class ForApp

@Module
class SourceModule (private val application: Application){

    @Provides
//    @ForApp
    fun provideContext(): Context = application

    @Provides
    fun provideRemoteSource(): ExhibitsRemoteSource = ExhibitsRemoteSource()

    @Provides
    fun provideLocalSource(): ExhibitLocalSource = ExhibitLocalSource()

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
    internal fun provideAPI(): IExhibitionsApi = provideRetrofit().create(IExhibitionsApi::class.java)

    @Provides
    @Reusable
    fun provideNetworkDataObservable(): Single<Exhibitions> = provideAPI().getAPIExhibitions()
}

@Module
class RoomModule(val context: Context) {
    companion object {
        const val databaseName = "product_database"
    }

    @Provides
    @Reusable
    fun provideDataBase(): ExhibitRoomDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            ExhibitRoomDataBase::class.java,
            databaseName
        ).build()

    @Provides
    fun provideDao(): ExhibitDao = provideDataBase().exhibitDao()

    @Provides
    fun provideLocalRepository(exhibitDao: ExhibitDao): ExhibitDatabaseRepository = ExhibitDatabaseRepository(exhibitDao)
}