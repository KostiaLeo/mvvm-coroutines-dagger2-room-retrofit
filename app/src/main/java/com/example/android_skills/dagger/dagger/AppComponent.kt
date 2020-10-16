package com.example.android_skills.dagger.dagger

import android.app.Application
import com.example.android_skills.dagger.dagger.view_model_modules.ViewModelModule
import com.example.android_skills.view.MainActivity
import com.example.android_skills.view.fragments.FavouritesFragment
import com.example.android_skills.view.fragments.StoriesFragment
import com.example.android_skills.view.fragments.VideoFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [AndroidSupportInjectionModule::class, ViewModelModule::class, RemoteModule::class, ViewModule::class, FragmentModule::class]
)
@Singleton
interface AppComponent {
    fun inject(application: App)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

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
    abstract fun contributeStoriesFragmentViewModelInjector(): StoriesFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeVideoFragmentViewModelInjector(): VideoFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeFavouritesFragmentViewModelInjector(): FavouritesFragment
}