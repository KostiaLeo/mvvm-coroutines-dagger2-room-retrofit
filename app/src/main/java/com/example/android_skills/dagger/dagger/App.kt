package com.example.android_skills.dagger.dagger

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App: Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun androidInjector(): AndroidInjector<Any>? = dispatchingAndroidInjector as AndroidInjector<Any>

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent.builder()
            .bindApplication(this)
            .setLocalModule(LocalModule(applicationContext))
            .setNetModule(NetModule(applicationContext))
            .build()
        appComponent.inject(this)
        super.onCreate()
    }
}