package com.example.restkotlinized.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restkotlinized.databinding.ListItemBinding
import com.example.restkotlinized.model.pojo.Result
import com.example.restkotlinized.viewmodel.MainViewModel
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.Disposable
import kotlin.collections.ArrayList

class NewsAdapter(private val films: ArrayList<Result>, private val viewModel: MainViewModel) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var disposableSetItem: Disposable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding, viewModel)
    }

    override fun getItemCount(): Int = films.let { films.size }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(films[position])

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        disposableSetItem?.dispose()
    }


    inner class ViewHolder(
        private val binding: ListItemBinding,
        private val viewModel: MainViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(film: Result) {
            binding.film = film
            binding.executePendingBindings()

            disposableSetItem = RxView.clicks(binding.root).subscribe {
                viewModel.selectItem(films[layoutPosition])
            }
        }
    }
}