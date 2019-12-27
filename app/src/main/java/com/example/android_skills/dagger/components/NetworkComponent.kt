package com.example.android_skills.dagger.components

import com.example.android_skills.dagger.NetworkUtils
import com.example.android_skills.dagger.SimpleDao
import com.example.android_skills.dagger.annotations.DefaultNetwork
import com.example.android_skills.dagger.annotations.UrlNetwork
import com.example.android_skills.dagger.modules.NetworkDBModule
import com.example.android_skills.dagger.modules.NetworkServerModule
import dagger.Subcomponent

@Subcomponent(modules = [NetworkServerModule::class, NetworkDBModule::class])
interface NetworkComponent {

    @DefaultNetwork
    fun getNetworkUtilsDefault() : NetworkUtils

    @UrlNetwork
    fun getNetworkUtilsByUrl() : NetworkUtils

    fun getNetworkDB() : SimpleDao
}