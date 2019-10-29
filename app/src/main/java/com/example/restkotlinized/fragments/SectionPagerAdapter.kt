@file:Suppress("DEPRECATION")

package com.example.restkotlinized.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.restkotlinized.R

class SectionPagerAdapter(val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    companion object {
        val TAB_TITLES = arrayOf(R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3)
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> StoriesFragment.create(context)
            1 -> VideoFragment.create(context)
            2 -> FavFragment.create(context)
            else -> StoriesFragment.create(context)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = context.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = TAB_TITLES.size
}