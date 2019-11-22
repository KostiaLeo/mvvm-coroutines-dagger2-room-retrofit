package com.example.restkotlinized.view.fragments.topAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restkotlinized.R
import com.example.restkotlinized.model.Results

class TopNewzAdapter (val results: ArrayList<Results>) : RecyclerView.Adapter<TopViewHolder>() {
    companion object {
        const val AMOUNT_OF_TOPNEWS = 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder =
        TopViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.top_new, parent, false))

    override fun getItemCount(): Int = results?.let { AMOUNT_OF_TOPNEWS }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        val result = results[position]
        val image = result.image
        Glide.with(holder.itemView.context)
            .load(image.url)
            .into(holder.photo)
        holder.nameTv.setText(result.name)
        holder.typeTv.setText(result.price)
        holder.costTv.setText(result.currency.id)
    }
}