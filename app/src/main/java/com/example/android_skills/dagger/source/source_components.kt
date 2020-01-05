package com.example.android_skills.dagger.daggerVM.source

import com.example.android_skills.dagger.daggerVM.viewmodel_factory.RetrofitScope
import com.example.android_skills.dagger.daggerVM.viewmodel_factory.RoomScope
import com.example.android_skills.dagger.daggerVM.viewmodel_factory.SourceScope
import com.example.android_skills.dagger.source.RetrofitModule
import com.example.android_skills.dagger.source.RoomModule
import com.example.android_skills.dagger.source.SourceModule
import com.example.android_skills.model.ModelRepository
import com.example.android_skills.model.NetManager
import com.example.android_skills.model.remote.ArtistsRemoteSource
import com.example.android_skills.model.sqlite.ArtistDao
import com.example.android_skills.model.sqlite.ArtistDatabaseRepository
import com.example.android_skills.model.sqlite.ArtistsLocalSource
import dagger.Subcomponent

@Subcomponent(modules = [SourceModule::class])
@SourceScope
interface SourceComponent {
    fun createRetrofitComponent(): RetrofitComponent
    fun createRoomComponent(roomModule: RoomModule): RoomComponent
    fun inject(modelRepository: ModelRepository)

// provide-methods for testing
    fun provideLocalSource(): ArtistsLocalSource
    fun provideRemoteSource(): ArtistsRemoteSource
    fun provideGeneralRepository(): ModelRepository
    fun provideNetManager(): NetManager
}

@Subcomponent(modules = [RetrofitModule::class])
@RetrofitScope
interface RetrofitComponent{
    fun inject(artistsRemoteSource: ArtistsRemoteSource)
}

@Subcomponent(modules = [RoomModule::class])
@RoomScope
interface RoomComponent{
    fun inject(artistsLocalSource: ArtistsLocalSource)
    fun provideDao(): ArtistDao
    fun provideDatabaseRepository(): ArtistDatabaseRepository
}