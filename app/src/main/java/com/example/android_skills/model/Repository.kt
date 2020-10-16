package com.example.android_skills.model

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android_skills.model.data.Item
import com.example.android_skills.model.source.ItemsApi
import com.example.android_skills.model.source.ItemsDataSource
import javax.inject.Inject

class Repository @Inject constructor(private val api: ItemsApi) {
    var topItemsLiveData: LiveData<PagedList<Item>>

    init {
        val config = PagedList.Config.Builder().setPageSize(5).setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5).build()
        val dataFactory = object : DataSource.Factory<Int, Item>() {
            override fun create(): DataSource<Int, Item> {
                return ItemsDataSource(api) { it.top == "1" }
            }
        }
        topItemsLiveData = LivePagedListBuilder(dataFactory, config).build()
    }
}