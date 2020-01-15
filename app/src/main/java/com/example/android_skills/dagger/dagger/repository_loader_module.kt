package com.example.android_skills.dagger.dagger

import com.example.android_skills.model.ExhibitsLoader
import com.example.android_skills.model.ModelRepository
import com.example.android_skills.model.NetManager
import com.example.android_skills.model.remote.ExhibitsRemoteSource
import com.example.android_skills.model.room.ExhibitLocalSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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