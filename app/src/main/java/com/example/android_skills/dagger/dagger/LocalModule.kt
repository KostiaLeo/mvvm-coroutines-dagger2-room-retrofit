package com.example.android_skills.dagger.dagger

import android.content.Context
import androidx.room.Room
import com.example.android_skills.model.LocalSource
import com.example.android_skills.model.room.ExhibitDao
import com.example.android_skills.model.room.ExhibitLocalSource
import com.example.android_skills.model.room.ExhibitRoomDataBase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LocalModule::class])
abstract class LocalSourceModule {
    @Binds
    @Singleton
    abstract fun bindLocalSource(exhibitLocalSource: ExhibitLocalSource): LocalSource
}

@Module
class LocalModule(private val context: Context) {
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
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideDao(exhibitRoomDataBase: ExhibitRoomDataBase): ExhibitDao =
        exhibitRoomDataBase.exhibitDao()

//    @Provides
//    @Singleton
//    fun provideLocalRepository(exhibitDao: ExhibitDao): ExhibitDatabaseRepository =
//        ExhibitDatabaseRepository(exhibitDao)

    @Provides
    @Singleton
    fun provideLocalSource(exhibitDao: ExhibitDao): ExhibitLocalSource =
        ExhibitLocalSource(exhibitDao)
}