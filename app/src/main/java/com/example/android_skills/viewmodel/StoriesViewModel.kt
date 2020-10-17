package com.example.android_skills.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.android_skills.dagger.modules.StoriesLiveData
import com.example.android_skills.dagger.modules.TopNewsLiveData
import com.example.android_skills.model.data.Item
import javax.inject.Inject

class StoriesViewModel @Inject constructor(
    @StoriesLiveData val itemsLiveData: LiveData<PagedList<Item>>,
    @TopNewsLiveData val topNewsLiveData: LiveData<PagedList<Item>>
) : ViewModel()