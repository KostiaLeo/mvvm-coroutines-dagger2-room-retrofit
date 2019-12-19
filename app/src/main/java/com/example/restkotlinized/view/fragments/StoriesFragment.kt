package com.example.restkotlinized.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.restkotlinized.R
import com.example.restkotlinized.databinding.FragmentStoriesBinding
import com.example.restkotlinized.model.pojo.Result
import com.example.restkotlinized.viewmodel.MainViewModel
import com.example.restkotlinized.view.adapters.NewsAdapter
import com.example.restkotlinized.viewmodel.MainViewModelFactory
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.collections.ArrayList

class StoriesFragment : Fragment() {
    private lateinit var newsRecycler: RecyclerView
    private lateinit var mainAdapter: NewsAdapter

    private lateinit var binding: FragmentStoriesBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private var yScrollCoordinate = 0
    private val yKey = "yKey"

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
        viewModelFactory = MainViewModelFactory(viewLifecycleOwner)
        viewModel =
            ViewModelProviders.of(activity!!, viewModelFactory)
                .get(MainViewModel::class.java)

        val isLoaded = viewModel.isLoaded.value!!

        if (!isLoaded) {
            viewModel.getDataArtists()
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stories, container, false)

        initUI()

        if (!isLoaded) {
            ReactiveNetwork
                .observeNetworkConnectivity(context)
                .subscribeOn(Schedulers.io())
                .retry(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        viewModel.getDataArtists()
                        initUI()
                        println("onNxt")
                    },
                    {
                        println(it)
                    }
                )
        }

        savedInstanceState?.let {
            val y = it.get(yKey) as Int
            println("$y - fromSave")
            Handler().postDelayed({
                binding.newzzz.scrollTo(0, y)
            }, 1000)
        }

        setScrollListener()

        return binding.root
    }

    private fun initUI() {
        binding.viewModel = viewModel
        binding.executePendingBindings()

        newsRecycler = binding.newzzz.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.filmsList.observe(viewLifecycleOwner,
            Observer {
                it?.let {
                    setAdapters(it)
                    mainAdapter.notifyDataSetChanged()
                }
            }
        )

        viewModel.titleClick.observe(viewLifecycleOwner, Observer {
            binding.newzzz.scrollToPosition(0)
        })
    }

    private fun setAdapters(allFilms: ArrayList<Result>) {
        mainAdapter = NewsAdapter(allFilms, viewModel)
        newsRecycler.adapter = mainAdapter
    }


    private fun setScrollListener() {
        binding.newzzz.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                yScrollCoordinate += dy
                println(recyclerView.getChildAdapterPosition(binding.newzzz))
            }
        })
    }
// --------------------------- END UI -------------------------------


    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)
        println("$yScrollCoordinate - onSave")
        outState.putInt(yKey, yScrollCoordinate)
    }
}