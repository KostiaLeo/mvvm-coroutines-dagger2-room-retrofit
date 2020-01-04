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
import com.example.android_skills.viewmodel.DaggerViewModel
import com.example.android_skills.dagger.daggerVM.viewmodel_factory.ViewModelFactory
import com.example.android_skills.dagger.daggerVM.extensions.injectViewModel
import com.example.android_skills.model.Results
import com.example.android_skills.view.adapters.NewsAdapter
import com.example.android_skills.view.adapters.TopNewsAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class StoriesFragment : DaggerFragment() {
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
        viewModel = injectViewModel(viewModelFactory)
        viewModel.getDataArtists()

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
        newsRecycler = root.findViewById(R.id.newzzz)
        viewPager2 = root.findViewById(R.id.viewPager2)
        nestedScrollView = root.findViewById(R.id.nested_scroll_view)


        newsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }

        viewPager2.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        viewModel.artistsList.observe(viewLifecycleOwner,
            Observer<ArrayList<Results>> {
                it?.let {
                    drawRecyclerView(it)
                }
            }
        )

        viewModel.titleClick.observe(viewLifecycleOwner, Observer {
            nestedScrollView.smoothScrollTo(0, 0)
        })
    }

    private fun drawRecyclerView(artists: ArrayList<Results>){
        mainAdapter = NewsAdapter(artists, viewModel)
        adapterForTopNews = TopNewsAdapter(artists)

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

        viewModel.apply {
            selectedItem.removeObservers(this@StoriesFragment)
            titleClick.removeObservers(this@StoriesFragment)
            artistsList.removeObservers(this@StoriesFragment)
        }
    }
}