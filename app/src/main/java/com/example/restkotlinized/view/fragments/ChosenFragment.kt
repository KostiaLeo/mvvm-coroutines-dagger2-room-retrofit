package com.example.restkotlinized.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.restkotlinized.R
import com.example.restkotlinized.model.pojo.Result
import com.example.restkotlinized.view.adapters.NewsAdapter
import com.example.restkotlinized.viewmodel.MainViewModel
import io.reactivex.disposables.Disposable

class ChosenFragment : Fragment() {
    private var root: View? = null
    private var disposable: Disposable? = null
    private lateinit var viewModel: MainViewModel

    companion object Factory {
        fun create(): ChosenFragment =
            ChosenFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_chosen, container, false)
        this.root = root
        observeDataFromClickObservable()
        return root
    }

    private fun observeDataFromClickObservable() {
        viewModel.selectedItem.observe(viewLifecycleOwner, Observer {
            setView(it)
        })
    }

    private fun setView(result: Result) {
        val nameTv = root?.findViewById<TextView>(R.id.name)
        val sourceTv = root?.findViewById<TextView>(R.id.source)
        val idTv = root?.findViewById<TextView>(R.id.id)
        val photo = root?.findViewById<ImageView>(R.id.photo)
        //val image = result?.image

        val basePath = "http://image.tmdb.org/t/p/w500"
        root?.context?.let {
            photo?.let { it1 ->
                Glide.with(it).load(basePath + result.backdrop_path).into(it1)
            }
        }
        nameTv?.text = result.title
        idTv?.text = result.release_date
        sourceTv?.text = result.overview
    }

    override fun onDetach() {
        super.onDetach()
        disposable?.dispose()
    }
}