package com.example.restkotlinized.view.mainAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restkotlinized.R
import com.example.restkotlinized.databinding.ListItemBinding
import com.example.restkotlinized.model.pojo.Results
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlin.collections.ArrayList

class NewzAdapter(val results: ArrayList<Results>) :
    RecyclerView.Adapter<NewzAdapter.ViewHolder>() {

    companion object {
        private val clickSubject = BehaviorSubject.create<Results>()
        val clickObservable: Observable<Results> =
            clickSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        private var disposableSetItem: Disposable? = null

        private val switchSubject = BehaviorSubject.create<Any>()
        val switchObservable: Observable<Any> = switchSubject
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //.inflate(R.layout.list_item, parent, false)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = results.let { results.size }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(results[position])

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Results) {
            binding.artist = artist

            disposableSetItem = RxView.clicks(binding.root).subscribe {
                clickSubject.onNext(results[layoutPosition])
                switchSubject.onNext(Any())
            }

            binding.executePendingBindings()
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        disposableSetItem?.dispose()
    }
}