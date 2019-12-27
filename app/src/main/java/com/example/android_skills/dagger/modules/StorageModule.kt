package com.example.android_skills.dagger.modules

import com.example.android_skills.dagger.DatabaseHelper
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

    @Provides
    fun provideDatabaseHelper() : DatabaseHelper {
        return DatabaseHelper()
    }
}