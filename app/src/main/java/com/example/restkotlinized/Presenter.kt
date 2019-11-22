package com.example.restkotlinized

import com.example.restkotlinized.model.Results

class Presenter(private val artistsView: ArtistsContract.View) : ArtistsContract.Presenter, ArtistsContract.Model.OnFinishedListener {

    private var artistsModel: ArtistsModel = ArtistsModel()

    override fun requestDataFromServer() {
        artistsModel.getArtistsList(this)
        artistsView.showProgress()
    }

    override fun onFinished(results: List<Results>) {
        artistsView.hideProgress()
        artistsView.setDataToRecyclerView(results)
    }

    override fun OnFailed(t: Throwable) {
        artistsView.onResponseFailure(t)
    //    artistsView?.hideProgress()
    }

    override fun onDestroy() {
//        artistsView = null
    }
}