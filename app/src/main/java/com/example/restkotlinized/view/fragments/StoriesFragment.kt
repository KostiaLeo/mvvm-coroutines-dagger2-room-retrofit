package com.example.restkotlinized.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.restkotlinized.ArtistsContract
import com.example.restkotlinized.Presenter
import com.example.restkotlinized.R
import com.example.restkotlinized.ShowEmptyView
import com.example.restkotlinized.model.Results
import com.example.restkotlinized.view.fragments.mainAdapter.NewzAdapter
import com.example.restkotlinized.view.fragments.topAdapter.TopNewzAdapter


class StoriesFragment(context: Context) : Fragment(), ArtistsContract.View, ShowEmptyView {

    private val ctx: Context = context
    private var newsRecycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var emptyView: TextView? = null

    private var presenter: Presenter = Presenter(this)

    private var allResults: ArrayList<Results>? = null

    private var mainAdapter: NewzAdapter? = null
    private var adapterForVP: TopNewzAdapter? = null

    companion object Factory {
        fun create(context: Context): StoriesFragment =
            StoriesFragment(context)
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_stories, container, false)
        presenter.requestDataFromServer()

        Presenter.loadObservable.subscribe {
            results -> initUI(root, ArrayList(results))
        }

        return root
    }

    private fun initUI(root: View, allResults: ArrayList<Results>){
        println("initUI(): allResults size = ${allResults.size}")

        mainAdapter = NewzAdapter(allResults)
        adapterForVP = TopNewzAdapter(allResults)

        val newsRecycler = root.findViewById<RecyclerView>(R.id.newzzz)
        newsRecycler.layoutManager = LinearLayoutManager(ctx)
        newsRecycler.itemAnimator = DefaultItemAnimator()
        progressBar = root.findViewById<ProgressBar>(R.id.pb_loading)

        val viewPager = root.findViewById<ViewPager2>(R.id.viewPager2)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val sliderDotsPanel = root.findViewById<LinearLayout>(R.id.SliderDots)
        setDots(sliderDotsPanel, viewPager)

        newsRecycler?.adapter = mainAdapter
        viewPager?.adapter = adapterForVP
    }

    override fun setDataToRecyclerView(results: List<Results>) {
            val res: ArrayList<Results> = ArrayList(results)
            println("setting adapter, res size = ${res.size}")
            allResults?.addAll(res)
            mainAdapter?.notifyDataSetChanged()
    }

    override fun onResponseFailure(throwable: Throwable) {
        Toast.makeText(
            context,
            "Сервер не отвечает, попробуйте позже",
            Toast.LENGTH_SHORT
        ).show()
        println(throwable)
    }

    override fun showEmptyView() {
        newsRecycler?.visibility = View.GONE
        emptyView?.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        newsRecycler?.visibility = View.VISIBLE
        emptyView?.visibility = View.GONE
    }

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.INVISIBLE
    }


// ------------------ UI ------------------
    private fun setDots(sliderDotsPanel: LinearLayout, viewPager: ViewPager2){

        sliderDotsPanel.bringToFront()
        val dotsCount = TopNewzAdapter.AMOUNT_OF_TOPNEWS

        val dots = arrayOfNulls<ImageView>(dotsCount)

        for(i in 1..dotsCount){
            dots[(i-1)] = ImageView(ctx)
        }

        dots.forEach {

            it?.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.dotgrey))
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(8, 0, 8, 0)
            params.gravity = Gravity.CENTER
            sliderDotsPanel.addView(it, params)
        }

        dots[0]?.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.dotblue))

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
                dots.forEach {
                    it?.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.dotgrey))
                    //val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                }
                dots[position]?.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.dotblue))
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }
}