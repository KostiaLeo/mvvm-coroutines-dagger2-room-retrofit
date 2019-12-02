package com.example.restkotlinized.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.restkotlinized.R
import com.example.restkotlinized.databinding.FragmentStoriesBinding
import com.example.restkotlinized.model.remote.Results
import com.example.restkotlinized.viewmodel.MainViewModel
import com.example.restkotlinized.view.adapters.NewzAdapter
import com.example.restkotlinized.view.adapters.TopNewzAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlin.collections.ArrayList

class StoriesFragment(context: Context) : Fragment() {
    private val ctx: Context = context
    private var root: View? = null
    private var newsRecycler: RecyclerView? = null
    private var viewPager2: ViewPager2? = null
    private var sliderDotsPanel: LinearLayout? = null

    private var mainAdapter: NewzAdapter? = null
    private var adapterForTopNewz: TopNewzAdapter? = null

    // --------------------------- Rx ------------------------------
    private val loadSubject = BehaviorSubject.create<List<Results>>()
    private val loadObservable =
        loadSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    private lateinit var disposableLoader: Disposable

    private lateinit var binding: FragmentStoriesBinding
    private lateinit var viewModel: MainViewModel


// --------------------- methods -------------------------------

    companion object Factory {
        fun create(context: Context): StoriesFragment =
            StoriesFragment(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.getData(this, this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stories, container, false)

        findUIElements()
        refreshUIByObserving()

        return binding.root
    }

    private fun findUIElements() {
        binding.viewModel = viewModel
        binding.executePendingBindings()

        newsRecycler = binding.newzzz.apply {
            layoutManager = LinearLayoutManager(context)
            viewModel.artistsList.observe(viewLifecycleOwner,
                Observer<ArrayList<Results>> {
                    it?.let {
                        loadSubject.onNext(it)
                        mainAdapter?.notifyDataSetChanged()
                    }
                }
            )
            itemAnimator = DefaultItemAnimator()
        }

        viewPager2 = binding.viewPager2.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    @SuppressLint("CheckResult")
    fun refreshUIByObserving() {
        disposableLoader = loadObservable.subscribe { results ->
            setAdapters(ArrayList(results))
        }
    }

    private fun setAdapters(allResults: ArrayList<Results>) {
        mainAdapter = NewzAdapter(allResults)
        adapterForTopNewz = TopNewzAdapter(allResults)
        newsRecycler?.adapter = mainAdapter
        viewPager2?.adapter = adapterForTopNewz
    }

// --------------------------- END UI -------------------------------


    override fun onDestroyView() {
        super.onDestroyView()
        disposableLoader.dispose()
    }
}

// sliderDotsPanel = root?.findViewById<LinearLayout>(R.id.SliderDots)
//        sliderDotsPanel?.let {
//            viewPager2?.let { pager -> setDots(sliderDotsPanel!!, pager) }
//        }
/* private fun setDots(sliderDotsPanel: LinearLayout, viewPager: ViewPager2) {

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
} */