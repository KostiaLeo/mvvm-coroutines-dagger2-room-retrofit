package com.example.restkotlinized.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.restkotlinized.R
import com.example.restkotlinized.view.adapters.NewzAdapter
import com.example.restkotlinized.view.fragments.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        disposable = NewzAdapter.switchObservable.subscribe {
            viewPager.currentItem = 1
        }
    }

    override fun onBackPressed() {
        viewPager.currentItem = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}