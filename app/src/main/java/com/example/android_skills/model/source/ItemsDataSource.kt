package com.example.android_skills.model.source

import androidx.paging.PageKeyedDataSource
import com.example.android_skills.model.data.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ItemsDataSource constructor(
    private val api: ItemsApi,
    private val filter: ((Item) -> Boolean)
) : PageKeyedDataSource<Int, Item>() {
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        scope.launch {
            val result = api.getAPIExhibitions(1).filter(filter)
            callback.onResult(result, 1, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        scope.launch {
            val result = api.getAPIExhibitions(params.key).filter(filter)
            callback.onResult(result, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}