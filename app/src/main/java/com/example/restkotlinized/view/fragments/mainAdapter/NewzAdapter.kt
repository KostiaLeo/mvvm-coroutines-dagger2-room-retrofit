package com.example.restkotlinized.view.fragments.mainAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restkotlinized.R
import com.example.restkotlinized.model.pojo.Results
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlin.collections.ArrayList

class NewzAdapter(val results: ArrayList<Results>) : RecyclerView.Adapter<NewzAdapter.ViewHolder>() {

    companion object {
        private val clickSubject = BehaviorSubject.create<Results>()
        val clickObservable: Observable<Results> =
            clickSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        private var disposableSetItem: Disposable? = null

        private val switchSubject = BehaviorSubject.create<Any>()
        val switchObservable: Observable<Any> = switchSubject
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    override fun getItemCount(): Int = results.let { results.size }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        val image = result.image
        Glide.with(holder.itemView.context)
            .load(image.url)
            .into(holder.photo)
        holder.nameTv.text = result.name
        holder.idTv.text = result.currency.id
        holder.sourceTv.text = result.price
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv = itemView.findViewById(R.id.name) as TextView
        val sourceTv = itemView.findViewById(R.id.source) as TextView
        val idTv = itemView.findViewById(R.id.id) as TextView
        val photo = itemView.findViewById(R.id.photo) as ImageView

        init {
            disposableSetItem = RxView.clicks(itemView).subscribe {
                clickSubject.onNext(results[layoutPosition])
                switchSubject.onNext(Any())
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        disposableSetItem?.dispose()
    }
}