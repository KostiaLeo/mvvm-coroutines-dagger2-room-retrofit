package com.example.android_skills.dagger.dagger

import android.content.Context
import com.example.android_skills.model.NetManager
import dagger.*
import javax.inject.Singleton

@Module
class NetModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideNetManager(): NetManager = NetManager(context)
}