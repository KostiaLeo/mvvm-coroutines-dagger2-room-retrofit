package com.example.restkotlinized.presenter

import android.content.Context
import retrofit2.Callback

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.restkotlinized.model.INewsApi
import com.example.restkotlinized.model.MyNewz
import com.example.restkotlinized.model.Results
import retrofit2.Call
import retrofit2.Response

class Newz() {//val viewPager2: ViewPager2, val recyclerView: RecyclerView, val context: Context
    fun getAllNews(){
        val apiNews = INewsApi.create()
            apiNews.getAPINewz("json").enqueue(object : Callback<MyNewz> {
                override fun onFailure(call: Call<MyNewz>, t: Throwable) {
                    println("Failure")
                }
                override fun onResponse(call: Call<MyNewz>, response: Response<MyNewz>) {
                    println("Successfully")
                    val myNewz = response.body()
                    if (myNewz != null) {
                        val resultList = ArrayList<Results>()
                        for(result:Results? in myNewz.results){
                            result?.let { resultList.add(it) }
                        }
                        setRecyclerView(resultList)
                    }
                }
            })
    }
private fun setRecyclerView(results: ArrayList<Results>){

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