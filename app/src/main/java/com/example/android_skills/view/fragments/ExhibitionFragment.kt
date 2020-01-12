package com.example.android_skills.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_skills.R
import com.example.android_skills.viewmodel.DaggerViewModel
import com.example.android_skills.dagger.daggerVM.viewmodel_factory.ViewModelFactory
import com.example.android_skills.dagger.daggerVM.extensions.injectViewModel
import com.example.android_skills.model.model_module_description.Exhibit
import com.example.android_skills.view.adapters.ExhibitParentAdapter

import dagger.android.support.DaggerFragment
import javax.inject.Inject


// Why do we need fragment? Yes, in accordance with task it's not mentioned, however
// in a case we want to implement onClick event to the recyclerView item it's better to interact exactly between fragment.
// Thus we can just create one more fragment and deal with it instead of creating other activities

class ExhibitionFragment : DaggerFragment() {
    private lateinit var root: View

    private lateinit var newsRecycler: RecyclerView
    private lateinit var mainAdapter: ExhibitParentAdapter
    private lateinit var nestedScrollView: NestedScrollView

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: DaggerViewModel

    private val yCoordinate = "y"


    companion object Factory {
        fun create(): ExhibitionFragment {
            return ExhibitionFragment()
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
                nestedScrollView.scrollTo(0, it.getInt(yCoordinate))
            }, 700)
        }

        return root
    }

    private fun initUI() {
        newsRecycler = root.findViewById(R.id.newzzz)
        nestedScrollView = root.findViewById(R.id.nested_scroll_view)

        newsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }

        viewModel.exhibitsList.observe(viewLifecycleOwner,
            Observer<ArrayList<Exhibit>> {
                it?.let {
                    drawRecyclerView(it)
                }
            }
        )

        viewModel.titleClick.observe(viewLifecycleOwner, Observer {
            nestedScrollView.smoothScrollTo(0, 0)
        })
    }

    private fun drawRecyclerView(exhibits: ArrayList<Exhibit>) {
        mainAdapter = ExhibitParentAdapter(exhibits)
        newsRecycler.adapter = mainAdapter
        mainAdapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(yCoordinate, nestedScrollView.scrollY)
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.exhibitsList.removeObservers(this@ExhibitionFragment)
    }
}