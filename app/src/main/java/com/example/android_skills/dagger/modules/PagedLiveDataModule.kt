package com.example.android_skills.dagger.modules

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android_skills.dagger.FragmentScope
import com.example.android_skills.model.data.Item
import com.example.android_skills.model.source.ItemsApi
import com.example.android_skills.model.source.ItemsDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
class PagedLiveDataModule {

    @FragmentScope
    @Provides
    @FavouritesLiveData
    fun provideFavouritesPagedLiveData(api: ItemsApi): LiveData<PagedList<Item>> {
        val config = PagedList.Config.Builder().setPageSize(5).setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5).build()
        val dataFactory = object : DataSource.Factory<Int, Item>() {
            override fun create(): DataSource<Int, Item> {
                return ItemsDataSource(api) { it.type == "favourites" }
            }
        }
        return LivePagedListBuilder(dataFactory, config).build()
    }

    @FragmentScope
    @Provides
    @VideoLiveData
    fun provideVideoPagedLiveData(api: ItemsApi): LiveData<PagedList<Item>> {
        val config = PagedList.Config.Builder().setPageSize(5).setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5).build()
        val dataFactory = object : DataSource.Factory<Int, Item>() {
            override fun create(): DataSource<Int, Item> {
                return ItemsDataSource(api) { it.type == "video" }
            }
        }
        return LivePagedListBuilder(dataFactory, config).build()
    }

    @FragmentScope
    @Provides
    @StoriesLiveData
    fun provideStoriesPagedLiveData(api: ItemsApi): LiveData<PagedList<Item>> {
        val config = PagedList.Config.Builder().setPageSize(5).setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5).build()
        val dataFactory = object : DataSource.Factory<Int, Item>() {
            override fun create(): DataSource<Int, Item> {
                return ItemsDataSource(api) { it.type == "strories" }
            }
        }
        return LivePagedListBuilder(dataFactory, config).build()
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StoriesLiveData

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FavouritesLiveData

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class VideoLiveData

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TopNewsLiveData