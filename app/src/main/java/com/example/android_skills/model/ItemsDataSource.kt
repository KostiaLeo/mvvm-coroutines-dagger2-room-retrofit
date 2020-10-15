package com.example.android_skills.model

import androidx.paging.PageKeyedDataSource
import com.example.android_skills.model.remote.IExhibitionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemsDataSource constructor(private val api: IExhibitionsApi, private val scope: CoroutineScope) : PageKeyedDataSource<Int, Item>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        scope.launch(Dispatchers.Main) {
            val result = api.getAPIExhibitions(1).filter { it.type == "favourites" }
            println("result: $result")
            callback.onResult(result, 1, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        scope.launch {
            val result = api.getAPIExhibitions(params.key).filter { it.type == "favourites" }
            println("resultAfter: $result")

            callback.onResult(result, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
//        scope.launch {
//            val result = api.getAPIExhibitions(params.key)
//            callback.onResult(result, params.key - 1)
//        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}