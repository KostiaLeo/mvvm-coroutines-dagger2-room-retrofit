package com.example.restkotlinized.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.restkotlinized.R
import com.example.restkotlinized.databinding.FragmentStoriesBinding
import com.example.restkotlinized.model.Results
import com.example.restkotlinized.viewmodel.MainViewModel
import com.example.restkotlinized.view.adapters.NewzAdapter
import com.example.restkotlinized.view.adapters.TopNewzAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import java.util.function.BiFunction
import kotlin.collections.ArrayList

class StoriesFragment : Fragment() {
    private var newsRecycler: RecyclerView? = null
    private var viewPager2: ViewPager2? = null

    private var mainAdapter: NewzAdapter? = null
    private var adapterForTopNewz: TopNewzAdapter? = null

    private val laManager = LinearLayoutManager(context)

    private lateinit var binding: FragmentStoriesBinding
    private lateinit var viewModel: MainViewModel

    private val X_NESTEDSCROLL_COORDINATE = "x"
    private val Y_NESTEDSCROLL_COORDINATE = "y"

// --------------------- methods -------------------------------

    companion object Factory {
        fun create(): StoriesFragment {
            return StoriesFragment()
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getData(this, this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stories, container, false)

        initUI()

        savedInstanceState?.let {
            Handler().postDelayed({
                binding.nestedScrollView.scrollTo(
                    it.getInt(X_NESTEDSCROLL_COORDINATE),
                    it.getInt(Y_NESTEDSCROLL_COORDINATE)
                )
            }, 700)
        }

        return binding.root
    }

    private fun initUI() {
        binding.viewModel = viewModel
        binding.executePendingBindings()

        newsRecycler = binding.newzzz.apply {
            layoutManager = laManager
            itemAnimator = DefaultItemAnimator()
        }

        viewPager2 = binding.viewPager2.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        viewModel.artistsList.observe(viewLifecycleOwner,
            Observer<ArrayList<Results>> {
                it?.let {
                    setAdapters(it)
                    mainAdapter?.notifyDataSetChanged()
                    adapterForTopNewz?.notifyDataSetChanged()
                }
            }
        )
    }

    private fun setAdapters(allResults: ArrayList<Results>) {
        mainAdapter = NewzAdapter(allResults)
        adapterForTopNewz = TopNewzAdapter(allResults)
        newsRecycler?.adapter = mainAdapter
        viewPager2?.adapter = adapterForTopNewz
    }

// --------------------------- END UI -------------------------------


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val x = binding.nestedScrollView.scrollX
        val y = binding.nestedScrollView.scrollY
        outState.putInt(X_NESTEDSCROLL_COORDINATE, x)
        outState.putInt(Y_NESTEDSCROLL_COORDINATE, y)
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