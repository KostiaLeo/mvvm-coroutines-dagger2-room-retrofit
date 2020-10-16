@file:Suppress("DEPRECATION")

package com.example.android_skills.view.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    companion object {
        val TAB_TITLES = arrayOf("Stories", "Video", "Favorites")
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StoriesFragment.newInstance()
            1 -> VideoFragment.newInstance()
            2 -> FavouritesFragment.newInstance()
            else -> StoriesFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? =
        TAB_TITLES[position]

    override fun getCount(): Int = TAB_TITLES.size
}