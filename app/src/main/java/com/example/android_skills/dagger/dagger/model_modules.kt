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
import com.example.android_skills.dagger.dagger.vm_factory.ViewModelFactory
import dagger.*
import dagger.multibindings.IntoMap
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME) @MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DaggerViewModel::class)
    abstract fun bindDaggerViewModel(daggerViewModel: DaggerViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}

@Module(includes = [ModuleRepository::class])
abstract class LoaderModule{
    @Binds
    @Singleton
    abstract fun bindLoader(modelRepository: ModelRepository): ExhibitsLoader
}

@Module(includes = [NetModule::class, RemoteModule::class, LocalModule::class])
class ModuleRepository {
    @Provides
    @Singleton
    fun provideMainRepository(
        netManager: NetManager,
        localSource: ExhibitLocalSource,
        remoteSource: ExhibitsRemoteSource
    ): ModelRepository = ModelRepository(
        netManager, localSource, remoteSource
    )
}

@Module
class NetModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideNetManager(): NetManager = NetManager(context)
}

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

@Module
class LocalModule(private val context: Context){
    companion object {
        const val databaseName = "product_database"
    }

    @Provides
    @Singleton
    fun provideDataBase(): ExhibitRoomDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            ExhibitRoomDataBase::class.java,
            databaseName
        ).build()

    @Provides
    @Singleton
    fun provideDao(exhibitRoomDataBase: ExhibitRoomDataBase): ExhibitDao = exhibitRoomDataBase.exhibitDao()

    @Provides
    @Singleton
    fun provideLocalRepository(exhibitDao: ExhibitDao): ExhibitDatabaseRepository = ExhibitDatabaseRepository(exhibitDao)

    @Provides
    @Singleton
    fun provideLocalSource(databaseRepository: ExhibitDatabaseRepository): ExhibitLocalSource = ExhibitLocalSource(databaseRepository)
}