package com.example.android_skills.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_skills.viewmodel.DaggerViewModel
import com.example.android_skills.databinding.ListItemBinding
import com.example.android_skills.model.Results
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.Disposable
import kotlin.collections.ArrayList

class NewsAdapter(private val results: ArrayList<Results>
                  , private val viewModel: DaggerViewModel
) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var disposableSetItem: Disposable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding
            , viewModel
        )
    }

    override fun getItemCount(): Int {
        return results.let { results.size }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(results[position])

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        disposableSetItem?.dispose()
    }


    inner class ViewHolder(
        private val binding: ListItemBinding,
        private val viewModel: DaggerViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Results) {
            binding.artist = artist
            binding.executePendingBindings()
            disposableSetItem = RxView.clicks(binding.root).subscribe {
                viewModel.selectItem(results[layoutPosition])
                println("clicked (Adapter)")
// -------- Alternative onClickListener via Rx ----------
//                clickSubject.onNext(results[layoutPosition])
//                switchSubject.onNext(Any())
            }
        }
    }
    // ------- alternative Rx click listener -------
//    companion object {
//        private val clickSubject = BehaviorSubject.create<Results>()
//        val clickObservable: Observable<Results> =
//            clickSubject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//
//        private val switchSubject = PublishSubject.create<Any>()
//        val switchObservable: Observable<Any> =
//            switchSubject.observeOn(AndroidSchedulers.mainThread())
//    }
}