package com.example.android_skills.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewpager.widget.ViewPager
import com.example.android_skills.R
import com.example.android_skills.dagger.DaggerApp
import com.example.android_skills.dagger.daggerVM.DaggerViewModel
import com.example.android_skills.dagger.daggerVM.ViewModelFactory
import com.example.android_skills.dagger.daggerVM.injectViewModel
import com.example.android_skills.view.fragments.SectionPagerAdapter
import com.example.android_skills.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null
    private lateinit var viewPager: ViewPager
    private lateinit var title: TextView

    private val mainPageNum = 0
    private val detailedPageNum = 1

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: DaggerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerApp.viewModelComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)

        viewModel = injectViewModel(viewModelFactory)

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        title = findViewById(R.id.titleApp)

        title.setOnClickListener {
            viewModel.onTitleClick()
            viewPager.currentItem = mainPageNum
        }

        viewModel.selectedItem.observe(this, Observer {
            println("caught (Activity)")
            viewPager.currentItem = detailedPageNum
        })

// -------- Alternative onClickListener via Rx ----------
//        disposable = NewsAdapter.switchObservable.subscribe {
//            viewPager.currentItem = detailedPageNum
//        }
    }

    override fun onBackPressed() {
        viewPager.currentItem = mainPageNum
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
