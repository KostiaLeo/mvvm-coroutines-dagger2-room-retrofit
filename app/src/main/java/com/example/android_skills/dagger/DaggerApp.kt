package com.example.android_skills.dagger

import android.app.Application
import com.example.android_skills.dagger.daggerVM.*

class DaggerApp : Application() {

    // TODO: apply Dagger to Retrofit and Room + research AndroidInjector for Activity & Fragment
    
    companion object {
        lateinit var viewModelComponent: AppComponent
        lateinit var sourceComponent: SourceComponent
    }

    override fun onCreate() {
        super.onCreate()
        viewModelComponent = DaggerAppComponent.create()
        sourceComponent = viewModelComponent.createSourceComponent(SourceModule(this))
    }
}