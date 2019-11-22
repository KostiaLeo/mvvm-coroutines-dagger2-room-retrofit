package com.example.restkotlinized

import com.example.restkotlinized.model.MyNewz
import com.example.restkotlinized.model.Results

interface ArtistsContract {

    interface Model {

        interface OnFinishedListener {
            fun onFinished(results: List<Results>)

            fun OnFailed(t: Throwable)
        }

        fun getArtistsList(onFinishedListener: OnFinishedListener)
    }

    interface View {
        fun showProgress()

        fun hideProgress()

        fun setDataToRecyclerView(results: List<Results>)

        fun onResponseFailure(throwable: Throwable)
    }

    interface Presenter{
        fun onDestroy()

        fun requestDataFromServer()
    }
}