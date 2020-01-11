package com.example.android_skills.dagger.source

import com.example.android_skills.dagger.daggerVM.viewmodel_factory.RetrofitScope
import com.example.android_skills.dagger.daggerVM.viewmodel_factory.RoomScope
import com.example.android_skills.dagger.daggerVM.viewmodel_factory.SourceScope
import com.example.android_skills.model.ModelRepository
import com.example.android_skills.model.NetManager
import com.example.android_skills.model.remote.ExhibitsRemoteSource
import com.example.android_skills.model.room.ExhibitDao
import com.example.android_skills.model.room.ExhibitDatabaseRepository
import com.example.android_skills.model.room.ExhibitLocalSource
import dagger.Subcomponent

@Subcomponent(modules = [SourceModule::class])
@SourceScope
interface SourceComponent {
    fun createRetrofitComponent(): RetrofitComponent
    fun createRoomComponent(roomModule: RoomModule): RoomComponent
    fun inject(modelRepository: ModelRepository)

// provide-methods for testing
    fun provideLocalSource(): ExhibitLocalSource
    fun provideRemoteSource(): ExhibitsRemoteSource
    fun provideGeneralRepository(): ModelRepository
    fun provideNetManager(): NetManager
}

@Subcomponent(modules = [RetrofitModule::class])
@RetrofitScope
interface RetrofitComponent{
    fun inject(exhibitsRemoteSource: ExhibitsRemoteSource)
}

@Subcomponent(modules = [RoomModule::class])
@RoomScope
interface RoomComponent{
    fun inject(exhibitLocalSource: ExhibitLocalSource)
    fun provideDatabaseRepository(): ExhibitDatabaseRepository
    fun provideDao(): ExhibitDao
}