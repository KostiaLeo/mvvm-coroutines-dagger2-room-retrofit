package com.example.android_skills.viewmodel

import com.example.android_skills.model.data.Item
import com.example.android_skills.model.source.ItemsApi
import javax.inject.Inject

class StoriesViewModel @Inject constructor(
    api: ItemsApi
) : BaseViewModel(api) {
    override val filter: (Item) -> Boolean
        get() =  { it.type == "strories" }
}