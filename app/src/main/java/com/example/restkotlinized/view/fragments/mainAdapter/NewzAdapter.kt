package com.example.restkotlinized.view.fragments.mainAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restkotlinized.R
import com.example.restkotlinized.model.MyNewz
import com.example.restkotlinized.model.Results
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import java.util.*
import kotlin.collections.ArrayList

class NewzAdapter(val results: ArrayList<Results>) :
    RecyclerView.Adapter<NewzAdapter.ViewHolder>() {

    private val clickSubject = ReplaySubject.create<Results>()
    private var disposable: Disposable? = null
    val clickEvent: Observable<Results> = clickSubject


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
    )

    override fun getItemCount(): Int = results?.let { results.size }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        val image = result.image
        Glide.with(holder.itemView.context)
            .load(image.url)
            .into(holder.photo)
        holder.nameTv.setText(result.name)
        holder.idTv.setText(result.currency.id)
        holder.sourceTv.setText(result.price)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv = itemView.findViewById<TextView>(R.id.name)
        val sourceTv = itemView.findViewById<TextView>(R.id.source)
        val idTv = itemView.findViewById<TextView>(R.id.id)
        val photo = itemView.findViewById<ImageView>(R.id.photo)


        init {
            disposable = RxView.clicks(itemView).subscribe {
                clickSubject.onNext(results[layoutPosition])
            }
//            itemView.setOnClickListener {
//                clickSubject.onNext(results[layoutPosition])
//            }
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        disposable?.dispose()
    }
}