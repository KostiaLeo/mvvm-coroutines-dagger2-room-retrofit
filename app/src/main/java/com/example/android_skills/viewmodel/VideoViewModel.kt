package com.example.android_skills.viewmodel

import com.example.android_skills.model.TopNewsRepository
import com.example.android_skills.model.data.Item
import com.example.android_skills.model.source.ItemsApi
import javax.inject.Inject

class VideoViewModel @Inject constructor(
    api: ItemsApi, topNewsRepository: TopNewsRepository
) : BaseViewModel(api, topNewsRepository) {
    override val typeDataFilter: (Item) -> Boolean
        get() =  { it.type == "video" }
}