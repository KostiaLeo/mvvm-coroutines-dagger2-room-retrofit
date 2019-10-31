package com.example.restkotlinized.view.fragments.mainAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restkotlinized.R
import kotlinx.android.synthetic.main.list_item.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTv = itemView.findViewById<TextView>(R.id.name)
    val sourceTv = itemView.findViewById<TextView>(R.id.source)
    val idTv = itemView.findViewById<TextView>(R.id.id)
    val photo = itemView.findViewById<ImageView>(R.id.photo)
}