package com.example.restkotlinized.view.fragments

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.restkotlinized.R
import com.example.restkotlinized.presenter.Newz
import com.example.restkotlinized.view.fragments.topAdapter.TopNewzAdapter

//object StoriesFragment : Fragment() {
//    fun getStFrag(context: Context): StoriesFragment = StoriesFragment
//}
class StoriesFragment(context: Context) : Fragment() {
    val ctx = context
    companion object Factory {
        fun create(context: Context): StoriesFragment =
            StoriesFragment(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_stories, container, false)
        val newsRecycler = root.findViewById<RecyclerView>(R.id.newzzz)
        newsRecycler.layoutManager = LinearLayoutManager(this.ctx)

        val viewPager = root.findViewById<ViewPager2>(R.id.viewPager2)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val sliderDotsPanel = root.findViewById<LinearLayout>(R.id.SliderDots)
        sliderDotsPanel.bringToFront()
        val dotsCount = TopNewzAdapter.AMOUNT_OF_TOPNEWS

        val dots = arrayOfNulls<ImageView>(dotsCount)

        for(i in 1..dotsCount){
            dots[(i-1)] = ImageView(ctx)
        }

        dots.forEach {

            it?.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.dotgrey))
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(8, 0, 8, 0)
            params.gravity = Gravity.CENTER
            sliderDotsPanel.addView(it, params)
        }

        dots[0]?.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.dotblue))

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
                dots.forEach {
                    it?.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.dotgrey))
                    val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                }
                dots[position]?.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.dotblue))
            }
        })
        val news = Newz(viewPager, newsRecycler, ctx)
        news.getAllNews()
        return root
    }
}