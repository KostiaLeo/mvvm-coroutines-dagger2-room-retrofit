package com.example.android_skills.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.android_skills.dagger.dagger.view_model_modules.ViewModelFactory
import com.example.android_skills.dagger.extensions.injectViewModel
import com.example.android_skills.databinding.FragmentFavouritesBinding
import com.example.android_skills.view.paging.ItemAdapter
import com.example.android_skills.viewmodel.FavouritesViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavouritesFragment : DaggerFragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var newsRecycler: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ItemAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: FavouritesViewModel

    companion object {
        @JvmStatic
        fun newInstance() = FavouritesFragment()
    }
    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        binding = FragmentFavouritesBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        initUI()

        viewModel.itemsLiveData.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = View.GONE
            adapter.submitList(it)
        })

        return binding.root
    }

    private fun initUI() {
        progressBar = binding.progressBar

        newsRecycler = binding.favouritesRecycler.also {
            with(it) {
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            }
        }
        adapter = ItemAdapter()
        newsRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.itemsLiveData.removeObservers(viewLifecycleOwner)
    }
}