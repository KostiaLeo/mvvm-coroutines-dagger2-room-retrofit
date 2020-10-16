package com.example.android_skills.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android_skills.model.Item
import com.example.android_skills.model.ItemsDataSource
import com.example.android_skills.model.remote.ItemsApi
import javax.inject.Inject

class StoriesViewModel @Inject constructor(
    private val api: ItemsApi
) : ViewModel() {

    var itemsLiveData: LiveData<PagedList<Item>>

    init {
        val config = PagedList.Config.Builder().setPageSize(5).setEnablePlaceholders(false).setInitialLoadSizeHint(5).build()
        val dataFactory = object : DataSource.Factory<Int, Item>() {
            override fun create(): DataSource<Int, Item> {
                return ItemsDataSource(api) { it.type == "strories" }
            }
        }
        itemsLiveData = LivePagedListBuilder(dataFactory, config).build()
    }

}