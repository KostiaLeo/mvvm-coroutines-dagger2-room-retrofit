package com.example.android_skills.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android_skills.model.Item
import com.example.android_skills.model.ItemsDataSource
import com.example.android_skills.model.remote.IExhibitionsApi
import javax.inject.Inject

class DaggerViewModel @Inject constructor(
    private val api: IExhibitionsApi
) : ViewModel() {

    var itemsLiveData: LiveData<PagedList<Item>>

    init {
        val config = PagedList.Config.Builder().setInitialLoadSizeHint(2).setPageSize(2).build()
        val dataFactory = object : DataSource.Factory<Int, Item>() {
            override fun create(): DataSource<Int, Item> {
                return ItemsDataSource(api, viewModelScope)
            }
        }
        itemsLiveData = LivePagedListBuilder(dataFactory, config).build()
    }
}