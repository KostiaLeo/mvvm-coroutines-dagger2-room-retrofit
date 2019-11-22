package com.example.restkotlinized.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.restkotlinized.R
import com.example.restkotlinized.view.fragments.SectionPagerAdapter
import com.example.restkotlinized.view.fragments.mainAdapter.NewzAdapter
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.observers.DisposableCompletableObserver

class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionPagerAdapter = SectionPagerAdapter(
            this,
            supportFragmentManager
        )
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        disposable = NewzAdapter.switchObservable.subscribe {
            viewPager.currentItem = 1
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
