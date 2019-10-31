package com.example.restkotlinized.view.fragments.mainAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restkotlinized.R
import com.example.restkotlinized.model.Results

class NewzAdapter(val results: ArrayList<Results>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
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
}