package com.example.android_skills.dagger.daggerVM

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_skills.model.ModelRepository
import com.example.android_skills.model.NetManager
import com.example.android_skills.model.remote.ArtistsRemoteSource
import com.example.android_skills.model.sqlite.ArtistsLocalSource
import com.example.android_skills.view.MainActivity
import com.example.android_skills.view.fragments.ChosenFragment
import com.example.android_skills.view.fragments.StoriesFragment
import com.example.android_skills.viewmodel.DaggerViewModel
import dagger.*
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
class RepositoryProvider {

    @Provides
    fun bindModelRepository(): ModelRepository = ModelRepository()
}

@Module
class SourceModule (private val application: Application){

    @Provides
    fun provideRemoteSource(): ArtistsRemoteSource = ArtistsRemoteSource()

    @Provides
    fun provideLocalSource(): ArtistsLocalSource = ArtistsLocalSource(application)

    @Provides
    fun provideNetManager(): NetManager = NetManager(application)
}

@Component(modules = [ViewModelModule::class, RepositoryProvider::class])
@Singleton
interface AppComponent {
    fun createSourceComponent(sourceModule: SourceModule): SourceComponent

    fun inject(storiesFragment: StoriesFragment)
    fun inject(chosenFragment: ChosenFragment)
    fun inject(activity: MainActivity)

    fun inject(daggerViewModel: DaggerViewModel)
}

@Subcomponent(modules = [SourceModule::class])
interface SourceComponent {
    fun inject(modelRepository: ModelRepository)
}