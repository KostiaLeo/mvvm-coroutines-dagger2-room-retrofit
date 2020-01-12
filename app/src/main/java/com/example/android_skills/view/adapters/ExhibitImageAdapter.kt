package com.example.android_skills.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_skills.databinding.ExhibitImageBinding

class ExhibitImageAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ExhibitImageAdapter.ExhibitImgHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitImgHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ExhibitImageBinding.inflate(layoutInflater, parent, false)

        return ExhibitImgHolder(binding)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ExhibitImgHolder, position: Int) {
        holder.bind(images[position])
    }


    inner class ExhibitImgHolder(
        private val binding: ExhibitImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String){
            binding.imageUrl = imageUrl
            binding.executePendingBindings()
        }
    }
}