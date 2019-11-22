package com.example.restkotlinized.view.topAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restkotlinized.R
import com.example.restkotlinized.model.pojo.Results

class TopNewzAdapter (val results: ArrayList<Results>) : RecyclerView.Adapter<TopNewzAdapter.TopViewHolder>() {
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
        holder.nameTv.text = result.name
        holder.typeTv.text = result.price
        holder.costTv.text = result.currency.id
    }

    inner class TopViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv = itemView.findViewById(R.id.name_top) as TextView
        val costTv = itemView.findViewById(R.id.cost_top) as TextView
        val typeTv = itemView.findViewById(R.id.type_top) as TextView
        val photo = itemView.findViewById(R.id.photo_top) as ImageView
    }
}