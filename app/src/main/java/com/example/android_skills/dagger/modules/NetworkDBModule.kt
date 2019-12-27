package com.example.android_skills.dagger.modules

import com.example.android_skills.dagger.SimpleDao
import dagger.Module
import dagger.Provides

@Module
class NetworkDBModule(private val dao: String){

    @Provides
    fun provideDao(): SimpleDao {
        return SimpleDao(dao)
    }
}