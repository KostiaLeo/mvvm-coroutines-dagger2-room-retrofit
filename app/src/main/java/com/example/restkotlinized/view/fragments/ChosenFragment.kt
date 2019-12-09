package com.example.restkotlinized.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.restkotlinized.R
import com.example.restkotlinized.model.Results
import com.example.restkotlinized.view.adapters.NewzAdapter
import io.reactivex.disposables.Disposable

class ChosenFragment : Fragment() {
    private var root: View? = null
    private var disposable: Disposable? = null

    companion object Factory {
        fun create(): ChosenFragment =
            ChosenFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_chosen, container, false)
        this.root = root
        observeDataFromClickObservable()
        return root
    }

    private fun observeDataFromClickObservable() {
        disposable = NewzAdapter.clickObservable.subscribe { result ->
            setView(result)
        }
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