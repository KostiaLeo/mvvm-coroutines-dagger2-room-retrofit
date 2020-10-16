package com.example.android_skills.dagger.dagger.modules

import com.example.android_skills.dagger.dagger.FragmentScope
import com.example.android_skills.view.fragments.FavouritesFragment
import com.example.android_skills.view.fragments.StoriesFragment
import com.example.android_skills.view.fragments.VideoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeStoriesFragmentViewModelInjector(): StoriesFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeVideoFragmentViewModelInjector(): VideoFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeFavouritesFragmentViewModelInjector(): FavouritesFragment
}