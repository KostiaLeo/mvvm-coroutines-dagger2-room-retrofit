package com.example.android_skills.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.android_skills.R
import com.example.android_skills.model.Results
import com.example.android_skills.viewmodel.MainViewModel
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
// ------- alternative Rx click listener -------
//        disposable = NewsAdapter.clickObservable.subscribe { result ->
//            setView(result)
//        }
    }

    private fun setView(result: Results?) {
        val nameTv = root?.findViewById<TextView>(R.id.name)
        val sourceTv = root?.findViewById<TextView>(R.id.source)
        val idTv = root?.findViewById<TextView>(R.id.id)
        val photo = root?.findViewById<ImageView>(R.id.photo)
        val image = result?.image

        root?.context?.let {
            photo?.let { it1 ->
                Glide.with(it).load(image?.url).into(it1)
            }
        }
        nameTv?.text = result?.name
        idTv?.text = result?.currency?.id
        sourceTv?.text = result?.price.toString()
    }

    override fun onDetach() {
        super.onDetach()
        disposable?.dispose()
    }
}