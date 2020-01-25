package com.example.android_skills.dagger.dagger

import android.app.Application
import com.example.android_skills.dagger.dagger.view_model_modules.ViewModelModule
import com.example.android_skills.model.ModelRepository
import com.example.android_skills.model.remote.ExhibitsRemoteSource
import com.example.android_skills.model.room.ExhibitDao
import com.example.android_skills.model.room.ExhibitLocalSource
import com.example.android_skills.view.MainActivity
import com.example.android_skills.view.fragments.ExhibitionFragment
import com.example.android_skills.viewmodel.DaggerViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [AndroidSupportInjectionModule::class, ViewModelModule::class,
        FragmentModule::class, ModuleRepository::class, LocalModule::class,
        RemoteModule::class, NetModule::class, ViewModule::class, LoaderModule::class,
        RemoteSourceModule::class, LocalSourceModule::class]
)
@Singleton
interface AppComponent {
    fun inject(application: App)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun setNetModule(netModule: NetModule): Builder

        fun setLocalModule(localModule: LocalModule): Builder

        @BindsInstance
        fun bindApplication(application: Application): Builder
    }

    fun provideDao(): ExhibitDao

    fun provideLocalSrc(): ExhibitLocalSource

    fun provideRemoteSrc(): ExhibitsRemoteSource

    fun provideViewModel(): DaggerViewModel

    fun provideModelRepo(): ModelRepository
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