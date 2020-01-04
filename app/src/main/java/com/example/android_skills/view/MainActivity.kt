package com.example.android_skills.view

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewpager.widget.ViewPager
import com.example.android_skills.R
import com.example.android_skills.viewmodel.DaggerViewModel
import com.example.android_skills.dagger.daggerVM.viewmodel_factory.ViewModelFactory
import com.example.android_skills.dagger.daggerVM.extensions.injectViewModel
import com.example.android_skills.view.fragments.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), HasAndroidInjector {

    private var disposable: Disposable? = null
    private lateinit var viewPager: ViewPager
    private lateinit var title: TextView

    private val mainPageNum = 0
    private val detailedPageNum = 1

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: DaggerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
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
            viewPager.currentItem = detailedPageNum
        })

// -------- Alternative onClickListener via Rx ----------
//        disposable = NewsAdapter.switchObservable.subscribe {
//            viewPager.currentItem = detailedPageNum
//        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector as AndroidInjector<Any>

    override fun onBackPressed() {
        viewPager.currentItem = mainPageNum
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        viewModel.apply {
            selectedItem.removeObservers(this@MainActivity)
            titleClick.removeObservers(this@MainActivity)
            artistsList.removeObservers(this@MainActivity)
        }
    }
}
