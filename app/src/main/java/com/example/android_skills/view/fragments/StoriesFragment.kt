package com.example.android_skills.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.android_skills.dagger.dagger.view_model_modules.ViewModelFactory
import com.example.android_skills.dagger.extensions.injectViewModel
import com.example.android_skills.databinding.FragmentStoriesBinding
import com.example.android_skills.view.adapters.ItemAdapter
import com.example.android_skills.view.adapters.TopNewsAdapter
import com.example.android_skills.viewmodel.StoriesViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class StoriesFragment : DaggerFragment() {
    private lateinit var binding: FragmentStoriesBinding
    private lateinit var newsRecycler: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ItemAdapter
    private lateinit var topViewPager: ViewPager2
    private lateinit var topNewsAdapter: TopNewsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: StoriesViewModel


    companion object {
        @JvmStatic
        fun newInstance() = StoriesFragment()
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        binding = FragmentStoriesBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        initUI()

        viewModel.itemsLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            progressBar.visibility = View.GONE
        })

        viewModel.topNewsLiveData.observe(viewLifecycleOwner, Observer {
            topNewsAdapter.submitList(it)
        })

        return binding.root
    }

    private fun initUI() {
        progressBar = binding.progressBar

        newsRecycler = binding.storiesRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }
        adapter = ItemAdapter()
        newsRecycler.adapter = adapter

        topViewPager = binding.topViewPager
        topNewsAdapter = TopNewsAdapter()
        topViewPager.adapter = topNewsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.itemsLiveData.removeObservers(viewLifecycleOwner)
        viewModel.topNewsLiveData.removeObservers(viewLifecycleOwner)
    }
}