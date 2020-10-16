package com.example.android_skills.dagger.dagger

import com.example.android_skills.dagger.dagger.modules.SingletonModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [AndroidSupportInjectionModule::class, AndroidInjectionModule::class, SingletonModule::class]
)
@Singleton
interface AppComponent {
    fun inject(application: App)
}

