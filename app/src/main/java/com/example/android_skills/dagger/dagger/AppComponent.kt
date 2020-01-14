package com.example.android_skills.dagger.dagger

import android.app.Application
import com.example.android_skills.dagger.dagger.view_model_modules.ViewModelModule
import com.example.android_skills.view.MainActivity
import com.example.android_skills.view.fragments.ExhibitionFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [AndroidSupportInjectionModule::class, ViewModelModule::class, FragmentModule::class, ModuleRepository::class, LocalModule::class, RemoteModule::class, NetModule::class, ViewModule::class, LoaderModule::class])
@Singleton
interface AppComponent {
    fun inject(application: App)

    @Component.Builder
    interface Builder{
        fun build(): AppComponent

        fun setNetModule(netModule: NetModule): Builder

        fun setLocalModule(localModule: LocalModule): Builder

        @BindsInstance
        fun bindApplication(application: Application): Builder
    }
}

@Module
abstract class ViewModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeActivityAndroidInjector(): MainActivity
}

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeFragmentViewModelInjector(): ExhibitionFragment
}