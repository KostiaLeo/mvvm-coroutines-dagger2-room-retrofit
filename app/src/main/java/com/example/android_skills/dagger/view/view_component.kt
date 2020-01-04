package com.example.android_skills.dagger.daggerVM.view

import com.example.android_skills.dagger.daggerVM.viewmodel_factory.ViewScope
import com.example.android_skills.view.MainActivity
import com.example.android_skills.view.fragments.ChosenFragment
import com.example.android_skills.view.fragments.StoriesFragment
import com.example.android_skills.viewmodel.DaggerViewModel
import dagger.Subcomponent

@Subcomponent
@ViewScope
interface ViewComponent {
    fun inject(storiesFragment: StoriesFragment)
    fun inject(chosenFragment: ChosenFragment)
    fun inject(activity: MainActivity)

    fun inject(daggerViewModel: DaggerViewModel)
}