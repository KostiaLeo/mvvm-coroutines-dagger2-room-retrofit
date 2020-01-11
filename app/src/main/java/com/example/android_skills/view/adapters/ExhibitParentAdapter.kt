package com.example.android_skills.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.android_skills.databinding.ListItemBinding
import com.example.android_skills.model.model_module_description.Exhibit
import kotlin.collections.ArrayList

class ExhibitParentAdapter(private val exhibits: ArrayList<Exhibit>) :
    RecyclerView.Adapter<ExhibitParentAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = exhibits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(exhibits[position])
    }

    inner class ViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exhibit: Exhibit) {
            binding.exhibit = exhibit
            val childLayoutManager = LinearLayoutManager(binding.images.context, RecyclerView.HORIZONTAL, false)
            childLayoutManager.initialPrefetchItemCount = 4
            binding.images.apply {
                adapter = ExhibitImageAdapter(exhibit.images)
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
            binding.executePendingBindings()
        }
    }
}