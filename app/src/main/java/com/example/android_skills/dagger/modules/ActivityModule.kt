package com.example.android_skills.dagger.modules

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android_skills.dagger.ActivityScope
import com.example.android_skills.model.data.Item
import com.example.android_skills.model.source.ItemsApi
import com.example.android_skills.model.source.ItemsDataSource
import com.example.android_skills.view.MainActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit

@Module(includes = [FragmentBuilderModule::class])
class ActivityModule {

    @ActivityScope
    @Provides
    fun provideApi(retrofit: Retrofit): ItemsApi = retrofit.create(ItemsApi::class.java)

    @ActivityScope
    @Provides
    @TopNewsLiveData
    fun provideTopNewsPagedLiveData(api: ItemsApi): LiveData<PagedList<Item>> {
        val config = PagedList.Config.Builder().setPageSize(5).setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5).build()
        val dataFactory = object : DataSource.Factory<Int, Item>() {
            override fun create(): DataSource<Int, Item> {
                return ItemsDataSource(
                    api
                ) { it.top == "1" }
            }
        }
        return LivePagedListBuilder(dataFactory, config).build()
    }

}

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun contributeActivityAndroidInjector(): MainActivity
}