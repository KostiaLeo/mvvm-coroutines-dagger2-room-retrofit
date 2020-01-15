package com.example.android_skills.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_skills.R
import com.example.android_skills.dagger.dagger.view_model_modules.ViewModelFactory
import com.example.android_skills.viewmodel.DaggerViewModel
import com.example.android_skills.dagger.extensions.injectViewModel
import com.example.android_skills.model.Exhibit
import com.example.android_skills.view.adapters.ExhibitParentAdapter
import com.google.android.material.snackbar.Snackbar

import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


// Why do we need fragment? Yes, in accordance with task it's not mentioned, however
// in a case we want to implement onClick event for the recyclerView item it's better to interact exactly between fragment.
// Thus we can just create one more fragment and deal with it instead of creating other activities

class ExhibitionFragment : DaggerFragment() {
    private lateinit var root: View

    private lateinit var newsRecycler: RecyclerView
    private lateinit var mainAdapter: ExhibitParentAdapter
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var progressBar: ProgressBar

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

        root = inflater.inflate(R.layout.fragment_exhibits, container, false)

        initUI()

        observeExhibitsData()

        scrollToPreviousPosition(savedInstanceState)

        return root
    }

    private fun initUI() {
        progressBar = root.findViewById(R.id.progress_bar)

        newsRecycler = root.findViewById(R.id.newzzz)
        nestedScrollView = root.findViewById(R.id.nested_scroll_view)

        newsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun observeExhibitsData(){
        viewModel.getExhibitsList().observe(viewLifecycleOwner,
            Observer<ArrayList<Exhibit>> {
                it?.let {
                    progressBar.visibility = View.GONE
                    if(it == ArrayList(Collections.EMPTY_LIST)) {
                        Snackbar.make(this.root, "Check internet connection and reboot app", Snackbar.LENGTH_LONG).show()
                    } else {
                        drawRecyclerView(it)
                    }
                }
            }
        )
    }

    private fun drawRecyclerView(exhibits: ArrayList<Exhibit>) {
        mainAdapter = ExhibitParentAdapter(exhibits)
        newsRecycler.adapter = mainAdapter
        mainAdapter.notifyDataSetChanged()
    }

    private fun scrollToPreviousPosition(bundle: Bundle?){
        bundle?.let {
            Handler().postDelayed({
                nestedScrollView.scrollTo(0, it.getInt(yCoordinate))
            }, 700)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(yCoordinate, nestedScrollView.scrollY)
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.getExhibitsList().removeObservers(this)
    }
}