package com.example.android_skills.dagger.view

import com.example.android_skills.dagger.daggerVM.viewmodel_factory.ViewScope
import com.example.android_skills.view.MainActivity
import com.example.android_skills.view.fragments.ExhibitionFragment
import com.example.android_skills.viewmodel.DaggerViewModel
import dagger.Subcomponent

@Subcomponent
@ViewScope
interface ViewComponent {
    fun inject(exhibitionFragment: ExhibitionFragment)
    fun inject(activity: MainActivity)

    fun inject(daggerViewModel: DaggerViewModel)
}