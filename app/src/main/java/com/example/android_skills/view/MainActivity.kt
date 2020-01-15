package com.example.android_skills.view

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.android_skills.R
import com.example.android_skills.dagger.dagger.view_model_modules.ViewModelFactory
import com.example.android_skills.viewmodel.DaggerViewModel
import com.example.android_skills.dagger.extensions.injectViewModel
import com.example.android_skills.view.fragments.SectionPagerAdapter
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), HasAndroidInjector {

    private lateinit var viewPager: ViewPager

// --------- Dagger 2 injections -----------

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun androidInjector(): AndroidInjector<Any> =
        dispatchingAndroidInjector as AndroidInjector<Any>

    private lateinit var installManager: SplitInstallManager
    private lateinit var request: SplitInstallRequest

// ------- Dagger 2 injections end ---------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        initModules()

        installManager.startInstall(request)
    }

    private fun initModules() {
        installManager = SplitInstallManagerFactory.create(this)
        request = SplitInstallRequest.newBuilder().addModule("model").build()
    }


    private fun initUI() {
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter

    }
}
