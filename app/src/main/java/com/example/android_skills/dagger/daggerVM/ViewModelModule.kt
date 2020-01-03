package com.example.android_skills.dagger.daggerVM

import android.app.Application
import android.graphics.ColorSpace
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_skills.model.ModelRepository
import com.example.android_skills.view.MainActivity
import com.example.android_skills.view.fragments.ChosenFragment
import com.example.android_skills.view.fragments.StoriesFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

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
class RepositoryProvider(val application: Application) {

    @Provides
    fun bindModelRepository(): ModelRepository = ModelRepository(application)
}

@Component(modules = [ViewModelModule::class, RepositoryProvider::class])
@Singleton
interface AppComponent {
    fun inject(storiesFragment: StoriesFragment)
    fun inject(chosenFragment: ChosenFragment)
    fun inject(activity: MainActivity)

    fun inject(daggerViewModel: DaggerViewModel)
}