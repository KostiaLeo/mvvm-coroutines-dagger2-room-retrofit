package com.example.android_skills.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_skills.databinding.ItemTopNewBinding
import com.example.android_skills.model.data.Item

class TopNewsAdapter : PagedListAdapter<Item, TopNewsAdapter.ItemViewHolder>(ItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemTopNewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    class ItemViewHolder(private val binding: ItemTopNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            binding.time.text = buildString {
                append(" - ")
                append(item.time)
            }
        }
    }
}