package com.example.restkotlinized.view.view_interface_impl

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
import com.example.restkotlinized.MVPContract
import com.example.restkotlinized.R
import com.example.restkotlinized.presenter.Presenter
import com.example.restkotlinized.model.pojo.Results
import com.example.restkotlinized.view.mainAdapter.NewzAdapter
import com.example.restkotlinized.view.topAdapter.TopNewzAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlin.collections.ArrayList

class StoriesFragment(context: Context) : Fragment(), MVPContract.View {
    companion object Factory {
        fun create(context: Context): StoriesFragment =
            StoriesFragment(context)
    }

    private val ctx: Context = context
    private var root: View? = null
    private var progressBar: ProgressBar? = null
    private var newsRecycler: RecyclerView? = null
    private var viewPager2: ViewPager2? = null
    private var sliderDotsPanel: LinearLayout? = null

    private var presenter: Presenter = Presenter(this)

    private var mainAdapter: NewzAdapter? = null
    private var adapterForTopNewz: TopNewzAdapter? = null

    private val loadSubject = BehaviorSubject.create<List<Results>>()
    private val loadObservable =
        loadSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    private var disposableLoader: Disposable? = null


    override fun setDataToRecyclerView(results: List<Results>) {
        loadSubject.onNext(results)
        mainAdapter?.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_stories, container, false)
        this.root = root
        presenter.requestDataFromServer()
        findUIElements()
        refreshUIByObserving()

        return root
    }

    @SuppressLint("CheckResult")
    fun refreshUIByObserving() {
        disposableLoader = loadObservable.subscribe { results ->
            putResultsToInitUI(results)
        }
    }

    private fun putResultsToInitUI(r: List<Results>) {
        setAdaptersAndDots(ArrayList(r))
    }

// ----------------------------- UI ---------------------------------

    private fun findUIElements() {
        newsRecycler = root?.findViewById<RecyclerView>(R.id.newzzz)
        newsRecycler?.layoutManager = LinearLayoutManager(ctx)
        newsRecycler?.itemAnimator = DefaultItemAnimator()

        viewPager2 = root?.findViewById<ViewPager2>(R.id.viewPager2)
        viewPager2?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun setAdaptersAndDots(allResults: ArrayList<Results>) {
        mainAdapter = NewzAdapter(allResults)
        adapterForTopNewz = TopNewzAdapter(allResults)

        newsRecycler?.adapter = mainAdapter
        viewPager2?.adapter = adapterForTopNewz

        sliderDotsPanel = root?.findViewById<LinearLayout>(R.id.SliderDots)
        sliderDotsPanel?.let {
            viewPager2?.let { pager -> setDots(sliderDotsPanel!!, pager) }
        }
    }

    private fun setDots(sliderDotsPanel: LinearLayout, viewPager: ViewPager2) {

        sliderDotsPanel.bringToFront()
        val dotsCount = TopNewzAdapter.AMOUNT_OF_TOPNEWS

        val dots = arrayOfNulls<ImageView>(dotsCount)

        for (i in 1..dotsCount) {
            dots[(i - 1)] = ImageView(ctx)
        }

        dots.forEach {

            it?.setImageDrawable(
                ContextCompat.getDrawable(
                    ctx,
                    R.drawable.dotgrey
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            params.gravity = Gravity.CENTER
            sliderDotsPanel.addView(it, params)
        }

        dots[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                ctx,
                R.drawable.dotblue
            )
        )

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dots.forEach {
                    it?.setImageDrawable(
                        ContextCompat.getDrawable(
                            ctx,
                            R.drawable.dotgrey
                        )
                    )
                }
                dots[position]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        ctx,
                        R.drawable.dotblue
                    )
                )
            }
        })
    }

// --------------------------- END UI -------------------------------

    override fun onResponseFailure(throwable: Throwable) {
        Toast.makeText(context, "Сервер не отвечает, попробуйте позже", Toast.LENGTH_SHORT).show()
        println(throwable)
    }


    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.INVISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        disposableLoader?.dispose()
    }
}