package com.example.android_skills.dagger

import javax.inject.Inject
import javax.inject.Named

class Repository {

    @Named("byUrl")
    @Inject
    lateinit var networkUtils: NetworkUtils

    @Inject
    lateinit var databaseHelper: DatabaseHelper

    init {
        //DaggerApp.component.injectRepository(this)
    }

    fun showNetworkUrl(){
        println(networkUtils.showUrl())
    }
}