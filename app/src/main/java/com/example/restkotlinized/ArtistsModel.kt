package com.example.restkotlinized

import android.annotation.SuppressLint
import android.widget.Toast
import com.example.restkotlinized.model.INewsApi
import com.example.restkotlinized.model.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class ArtistsModel : ArtistsContract.Model {

    @SuppressLint("CheckResult")
    override fun getArtistsList(onFinishedListener: ArtistsContract.Model.OnFinishedListener) {
            val newsSingle = INewsApi.create().getAPINewz()
            newsSingle.subscribeOn(Schedulers.io()).retry(3)
                .observeOn(AndroidSchedulers.mainThread()).subscribe { myNews, error ->
                    if (myNews != null) {
//                        val resultList = ArrayList<Results>()
//                            resultList.addAll(it.results)
                        myNews.let {
                            onFinishedListener.onFinished(it.results.toList())
                            println("Successfully retrieved")
                        }

                    } else {
                        println("Failure")
                        println(error)
                        onFinishedListener.OnFailed(error)
//                        Toast.makeText(
//                            context,
//                            "Сервер не отвечает, попробуйте позже",
//                            Toas  t.LENGTH_SHORT
//                        ).show()
                        //context.getDrawable(R.id.SliderDots)?.setVisible(false, false)
                    }
                }
    }

}