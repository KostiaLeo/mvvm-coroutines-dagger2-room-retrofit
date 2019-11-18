package com.example.restkotlinized.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import retrofit2.Callback

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.restkotlinized.R
import com.example.restkotlinized.model.INewsApi
import com.example.restkotlinized.model.MyNewz
import com.example.restkotlinized.model.Results
import com.example.restkotlinized.view.fragments.mainAdapter.NewzAdapter
import com.example.restkotlinized.view.fragments.topAdapter.TopNewzAdapter
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_stories.view.*
import retrofit2.Call
import retrofit2.Response

class Newz(
    private val viewPager2: ViewPager2,
    private val recyclerView: RecyclerView,
    private val context: Context
) {
    var subscribeTop: Disposable? = null

    @SuppressLint("CheckResult")
    fun getAllNews() {
        val newsSingle = INewsApi.create().getAPINewz("json")
        newsSingle.subscribeOn(Schedulers.io()).retry(3)
            .observeOn(AndroidSchedulers.mainThread()).subscribe { myNews, error ->
                if (myNews != null) {
                    val resultList = ArrayList<Results>()
                    myNews.let { resultList.addAll(it.results) }
                    setRecyclerView(resultList)
                } else {
                    println("Failure")
                    println(error.printStackTrace())
                    Toast.makeText(
                        context,
                        "Сервер не отвечает, попробуйте позже",
                        Toast.LENGTH_SHORT
                    ).show()
                    //context.getDrawable(R.id.SliderDots)?.setVisible(false, false)
                }
            }

    }

    private fun setRecyclerView(results: ArrayList<Results>) {
        val adapterForVP = TopNewzAdapter(results)
        val mainAdapter = NewzAdapter(results)
        viewPager2.adapter = adapterForVP
        recyclerView.adapter = mainAdapter

        subscribeTop = mainAdapter.clickEvent.subscribe {
            Toast.makeText(context, "Clicked on ${it.name}", Toast.LENGTH_LONG).show()
        }
    }



















//                object : DisposableSingleObserver<MyNewz>() {
//
//                    override fun onSuccess(myNewz: MyNewz) {
//                        val resultList = ArrayList<Results>()
//                        myNewz.let { resultList.addAll(it.results) }
//                        setRecyclerView(resultList)
//                    }
//
//                    @SuppressLint("ResourceType")
//                    override fun onError(e: Throwable) {
//                        println("Failure")
//                        println(e.printStackTrace())
//                        Toast.makeText(
//                            context,
//                            "Сервер не отвечает, попробуйте позже",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        context.getDrawable(R.id.SliderDots)?.setVisible(false, true)
//                    }
//                }

//        val apiNews = INewsApi.create()
//            apiNews.getAPINewz("json").enqueue(object : Callback<MyNewz> {
//                @SuppressLint("ResourceType")
//                override fun onFailure(call: Call<MyNewz>, t: Throwable) {
//                    println("Failure")
//                    println(t.printStackTrace())
//                    Toast.makeText(
//                        context,
//                        "Сервер не отвечает, попробуйте позже",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    context.getDrawable(R.id.SliderDots)?.setVisible(false, true)
//                }
//
//                override fun onResponse(call: Call<MyNewz>, response: Response<MyNewz>) {
//                    println("Successfully")
//                    response.body()?.let {
//                        val resultList = ArrayList<Results>()
//                        for(result:Results? in it.results){
//                            result?.let { resultList.add(it) }
//                        }
//                        setRecyclerView(resultList)
//                    }
//                    if (myNewz != null) {
//                        val resultList = ArrayList<Results>()
//                        for(result:Results? in myNewz.results){
//                            result?.let { resultList.add(it) }
//                        }
//                        setRecyclerView(resultList)
//                    }
//                }
//            })

// ----------------------- Callback via Extension function working with class which implements Callback and overriding onResponse & onFailure methods
/*    fun<T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
        val callBackKt = CallBackKt<T>()
        callback.invoke(callBackKt)
        this.enqueue(callBackKt)
    }

    open class CallBackKt<T>: Callback<T> {
        var onResponse: ((Response<T>) -> Unit)? = null
        var onFailure: ((t: Throwable?) -> Unit)? = null

        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure?.invoke(t)
            println("Failure")
            println(t.printStackTrace())
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            onResponse?.invoke(response)
            println("Success")
        }
    }
 */
}