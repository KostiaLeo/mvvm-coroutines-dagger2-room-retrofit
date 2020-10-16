package com.example.android_skills.viewmodel

import com.example.android_skills.model.Repository
import com.example.android_skills.model.data.Item
import com.example.android_skills.model.source.ItemsApi
import javax.inject.Inject

class StoriesViewModel @Inject constructor(
    api: ItemsApi, repository: Repository
) : BaseViewModel(api, repository) {
    override val typeDataFilter: (Item) -> Boolean
        get() =  { it.type == "strories" }
}