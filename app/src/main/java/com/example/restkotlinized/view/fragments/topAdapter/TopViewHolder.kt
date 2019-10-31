package com.example.restkotlinized.view.fragments.topAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restkotlinized.R

class TopViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTv = itemView.findViewById<TextView>(R.id.name_top)
    val costTv = itemView.findViewById<TextView>(R.id.cost_top)
    val typeTv = itemView.findViewById<TextView>(R.id.type_top)
    val photo = itemView.findViewById<ImageView>(R.id.photo_top)
}