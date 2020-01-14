package com.example.android_skills.dagger.dagger

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.android_skills.model.ModelRepository
import com.example.android_skills.model.NetManager
import com.example.android_skills.model.model_module_description.Exhibitions
import com.example.android_skills.model.model_module_description.ExhibitsLoader
import com.example.android_skills.model.remote.ExhibitsRemoteSource
import com.example.android_skills.model.remote.IExhibitionsApi
import com.example.android_skills.model.remote.NetworkUrl
import com.example.android_skills.model.room.ExhibitDao
import com.example.android_skills.model.room.ExhibitDatabaseRepository
import com.example.android_skills.model.room.ExhibitLocalSource
import com.example.android_skills.model.room.ExhibitRoomDataBase
import com.example.android_skills.viewmodel.DaggerViewModel
import com.example.android_skills.dagger.dagger.view_model_modules.ViewModelFactory
import dagger.*
import dagger.multibindings.IntoMap
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
class NetModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideNetManager(): NetManager = NetManager(context)
}