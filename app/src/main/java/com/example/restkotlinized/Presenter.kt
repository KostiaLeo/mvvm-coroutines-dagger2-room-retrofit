package com.example.restkotlinized

import com.example.restkotlinized.model.Results
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class Presenter(private val artistsView: ArtistsContract.View) : ArtistsContract.Presenter, ArtistsContract.Model.OnFinishedListener {

    companion object {
        private val loadSubject = BehaviorSubject.create<List<Results>>()
        val loadObservable: Observable<List<Results>> = loadSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    }

    private var artistsModel: ArtistsModel = ArtistsModel()

    //private var artistsView: ArtistsContract.View = artistsView

//    init {
//        artistsModel = ArtistsModel()
//    }

    override fun requestDataFromServer() {
        artistsModel.getArtistsList(this)
        artistsView.showProgress()
    }

    override fun onFinished(results: List<Results>) {
        loadSubject.onNext(results)
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