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
import kotlinx.android.synthetic.main.fragment_stories.view.*
import retrofit2.Call
import retrofit2.Response

class Newz(private val viewPager2: ViewPager2, private val recyclerView: RecyclerView, private val context: Context) {
    fun getAllNews(){
        val apiNews = INewsApi.create()
            apiNews.getAPINewz("json").enqueue(object : Callback<MyNewz> {
                @SuppressLint("ResourceType")
                override fun onFailure(call: Call<MyNewz>, t: Throwable) {
                    println("Failure")
                    println(t.printStackTrace())
                    Toast.makeText(
                        context,
                        "Сервер не отвечает, попробуйте позже",
                        Toast.LENGTH_SHORT
                    ).show()
                    context.getDrawable(R.id.SliderDots)?.setVisible(false, true)
                }

                override fun onResponse(call: Call<MyNewz>, response: Response<MyNewz>) {
                    println("Successfully")
                    response.body()?.let {
                        val resultList = ArrayList<Results>()
                        for(result:Results? in it.results){
                            result?.let { resultList.add(it) }
                        }
                        setRecyclerView(resultList)
                    }
//                    if (myNewz != null) {
//                        val resultList = ArrayList<Results>()
//                        for(result:Results? in myNewz.results){
//                            result?.let { resultList.add(it) }
//                        }
//                        setRecyclerView(resultList)
//                    }
                }
            })
    }
private fun setRecyclerView(results: ArrayList<Results>){
    viewPager2.adapter = TopNewzAdapter(results)
    recyclerView.adapter = NewzAdapter(results)
}
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