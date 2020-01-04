package com.example.android_skills.dagger.daggerVM.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.viewmodel.DaggerViewModel

inline fun <reified T : ViewModel> Fragment.injectViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}

inline fun <reified T : ViewModel> FragmentActivity.injectViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}

inline fun DaggerViewModel.injectRepository(daggerViewModel: DaggerViewModel) {
    DaggerApp.viewComponent.inject(daggerViewModel)
}