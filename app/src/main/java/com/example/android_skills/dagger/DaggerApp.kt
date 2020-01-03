package com.example.android_skills.dagger

import android.app.Application
import com.example.android_skills.dagger.daggerVM.AppComponent
import com.example.android_skills.dagger.daggerVM.DaggerAppComponent
import com.example.android_skills.dagger.daggerVM.RepositoryProvider

class DaggerApp : Application() {

    companion object {
        lateinit var viewModelComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        viewModelComponent = DaggerAppComponent.builder().repositoryProvider(RepositoryProvider(this)).build()
    }
}