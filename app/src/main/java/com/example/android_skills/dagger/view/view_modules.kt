package com.example.android_skills.dagger.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_skills.dagger.daggerVM.viewmodel_factory.ViewModelFactory
import com.example.android_skills.dagger.daggerVM.viewmodel_factory.ViewModelKey
import com.example.android_skills.model.ModelRepository
import com.example.android_skills.viewmodel.DaggerViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DaggerViewModel::class)
    internal abstract fun bindDaggerViewModel(daggerViewModel: DaggerViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}

@Module
class RepositoryProvider {

    @Provides
    fun bindModelRepository(): ModelRepository = ModelRepository()
}

