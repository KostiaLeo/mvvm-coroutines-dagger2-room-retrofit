package com.example.android_skills.dagger

import android.app.Activity
import android.app.Application
import com.example.android_skills.dagger.daggerVM.source.*
import com.example.android_skills.dagger.daggerVM.view.ViewComponent
import com.example.android_skills.dagger.source.RoomModule
import com.example.android_skills.dagger.source.SourceModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class DaggerApp : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun androidInjector(): AndroidInjector<Any>? = dispatchingAndroidInjector as AndroidInjector<Any>


    companion object {
        private lateinit var appComponent: AppComponent
        lateinit var sourceComponent: SourceComponent
        lateinit var retrofitComponent: RetrofitComponent
        lateinit var roomComponent: RoomComponent
        lateinit var viewComponent: ViewComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().bindApplication(this).build()
        appComponent.inject(this)
        viewComponent = appComponent.createViewComponent()
        sourceComponent = appComponent.createSourceComponent(SourceModule(this))
        retrofitComponent = sourceComponent.createRetrofitComponent()
        roomComponent = sourceComponent.createRoomComponent(RoomModule(this))
    }
}