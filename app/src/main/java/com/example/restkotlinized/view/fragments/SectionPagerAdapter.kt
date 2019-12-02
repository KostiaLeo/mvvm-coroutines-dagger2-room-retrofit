@file:Suppress("DEPRECATION")

package com.example.restkotlinized.view.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.restkotlinized.R

class SectionPagerAdapter(private val context: Context, private val fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    companion object {
        val TAB_TITLES = arrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StoriesFragment.create(context)
            1 -> ChosenFragment.create(context)
            else -> StoriesFragment.create(context)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? =
        context.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = TAB_TITLES.size
}