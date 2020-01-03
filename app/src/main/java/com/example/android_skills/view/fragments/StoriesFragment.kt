package com.example.android_skills.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.android_skills.R
import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.dagger.daggerVM.DaggerViewModel
import com.example.android_skills.dagger.daggerVM.ViewModelFactory
import com.example.android_skills.dagger.daggerVM.injectViewModel
import com.example.android_skills.model.Results
import com.example.android_skills.view.adapters.NewsAdapter
import com.example.android_skills.view.adapters.TopNewsAdapter
import javax.inject.Inject

class StoriesFragment : Fragment() {
    private lateinit var root: View

    private lateinit var newsRecycler: RecyclerView
    private lateinit var viewPager2: ViewPager2
    private lateinit var mainAdapter: NewsAdapter
    private lateinit var adapterForTopNews: TopNewsAdapter
    private lateinit var nestedScrollView: NestedScrollView

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: DaggerViewModel

    private val X_COORDINATE = "x"
    private val Y_COORDINATE = "y"


    private var results = ArrayList<Results>()
    // --------------------- methods -------------------------------

    companion object Factory {
        fun create(): StoriesFragment {
            return StoriesFragment()
        }
    }

    override fun onAttach(context: Context) {
        DaggerApp.viewModelComponent.inject(this)
        super.onAttach(context)
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.getDataArtists()

        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stories, container, false)

        root = inflater.inflate(R.layout.fragment_stories, container, false)

        initUI()
        savedInstanceState?.let {
            Handler().postDelayed({
                nestedScrollView.scrollTo(
                    it.getInt(X_COORDINATE),
                    it.getInt(Y_COORDINATE)
                )
            }, 700)
        }

        return root
    }

    private fun initUI() {
//        binding.viewModel = viewModel
//        binding.executePendingBindings()
        newsRecycler = root.findViewById(R.id.newzzz)
        viewPager2 = root.findViewById(R.id.viewPager2)
        nestedScrollView = root.findViewById(R.id.nested_scroll_view)


        newsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }
        //newsRecycler = binding.newzzz

        viewPager2.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        viewModel.artistsList.observe(viewLifecycleOwner,
            Observer<ArrayList<Results>> {
                it?.let {
                    mainAdapter = NewsAdapter(it, viewModel)
                    adapterForTopNews = TopNewsAdapter(it)

                    newsRecycler.adapter = mainAdapter
                    viewPager2.adapter = adapterForTopNews
                    mainAdapter.notifyDataSetChanged()
                    adapterForTopNews.notifyDataSetChanged()
                }
            }
        )

        viewModel.titleClick.observe(viewLifecycleOwner, Observer {
            nestedScrollView.smoothScrollTo(0, 0)
        })
    }

    private fun setAdapters(allResults: ArrayList<Results>) {
        //println("${allResults[0].name} - from setAdapters")
//
//        newsRecycler = binding.newzzz.apply {
//            layoutManager = LinearLayoutManager(context)
//            itemAnimator = DefaultItemAnimator()
//        }
//
//        viewPager2 = binding.viewPager2.apply {
//            orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        }

        mainAdapter = NewsAdapter(allResults
            , viewModel
        )

        adapterForTopNews = TopNewsAdapter(allResults)
        println(mainAdapter.itemCount)
        newsRecycler.adapter = mainAdapter
        viewPager2.adapter = adapterForTopNews

        mainAdapter.notifyDataSetChanged()
        adapterForTopNews.notifyDataSetChanged()
    }

 //--------------------------- END UI -------------------------------


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(X_COORDINATE,
            nestedScrollView.scrollX)
        outState.putInt(Y_COORDINATE,
            nestedScrollView.scrollY)
    }

    override fun onDetach() {
        super.onDetach()
        if (viewModel.artistsList.hasActiveObservers()) {
            viewModel.artistsList.removeObservers(viewLifecycleOwner)
        }
    }
}

// sliderDotsPanel = root?.findViewById<LinearLayout>(R.id.SliderDots)
//        sliderDotsPanel?.let {
//            viewPager2?.let { pager -> setDots(sliderDotsPanel!!, pager) }
//        }
/* private fun setDots(sliderDotsPanel: LinearLayout, viewPager: ViewPager2) {

    sliderDotsPanel.bringToFront()
    val dotsCount = TopNewsAdapter.AMOUNT_OF_TOPNEWS

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