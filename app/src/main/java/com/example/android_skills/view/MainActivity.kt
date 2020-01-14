package com.example.android_skills.view

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.android_skills.R
import com.example.android_skills.dagger.dagger.vm_factory.ViewModelFactory
import com.example.android_skills.viewmodel.DaggerViewModel
import com.example.android_skills.dagger.extensions.injectViewModel
import com.example.android_skills.view.fragments.SectionPagerAdapter
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

// --------- Dagger 2 injections -----------

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector as AndroidInjector<Any>

// ------- Dagger 2 injections end ---------

    private lateinit var viewModel: DaggerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = injectViewModel(viewModelFactory)

        initUI()
    }

    private fun initUI(){
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter

        title = findViewById(R.id.titleApp)

        title.setOnClickListener {
            viewModel.onTitleClick()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        viewModel.apply {
            titleClick.removeObservers(this@MainActivity)
            getExhibitsList().removeObservers(this@MainActivity)
        }
    }
}
