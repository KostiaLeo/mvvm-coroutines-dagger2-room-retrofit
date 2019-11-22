package com.example.restkotlinized.view.fragments

import android.content.Context
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
import com.example.restkotlinized.view.fragments.mainAdapter.NewzAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.fragment_stories.*

class VideoFragment(context: Context) : Fragment() {
    private val ctx = context
    private var root: View? = null
    private var disposable: Disposable? = null

    companion object Factory {
        fun create(context: Context): VideoFragment =
            VideoFragment(context)
    }

    // TODO: create observable which will emit some simple value to MainActivity for switching tabs
    //  when user has clicked on New-item

    // https://stackoverflow.com/questions/12231318/set-default-tab-for-fragment-viewpager-setup


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_video, container, false)
        this.root = root
        observeDataFromObservable()
        return root
    }

    private fun observeDataFromObservable() {
        disposable = NewzAdapter.clickObservable.subscribe { result ->
            setView(result)
        }
    }

    fun setView(result: Results?) {
        val nameTv = root?.findViewById<TextView>(R.id.name)
        val sourceTv = root?.findViewById<TextView>(R.id.source)
        val idTv = root?.findViewById<TextView>(R.id.id)
        val photo = root?.findViewById<ImageView>(R.id.photo)
        val image = result?.image

        root?.context?.let {
            photo?.let { it1 -> Glide.with(it).load(image?.url).into(it1) }
        }
        nameTv?.setText(result?.name)
        idTv?.setText(result?.currency?.id)
        sourceTv?.setText(result?.price)
    }

    override fun onDetach() {
        super.onDetach()
        disposable?.dispose()
    }
}