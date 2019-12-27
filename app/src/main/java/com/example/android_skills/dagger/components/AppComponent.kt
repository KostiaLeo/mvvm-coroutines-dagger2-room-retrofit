package com.example.android_skills.dagger.components

import com.example.android_skills.dagger.DatabaseHelper
import com.example.android_skills.dagger.NetworkUtils
import com.example.android_skills.dagger.SimpleDao
import com.example.android_skills.dagger.annotations.DefaultNetwork
import com.example.android_skills.dagger.annotations.UrlNetwork
import com.example.android_skills.dagger.modules.NetworkDBModule
import com.example.android_skills.dagger.modules.NetworkServerModule
import com.example.android_skills.dagger.modules.StorageModule
import dagger.Component

@Component(modules = [StorageModule::class])
interface AppComponent {
    /** inject-method
    fun injectRepository(repository: Repository)
    */

    /** get-methods */

    fun createNetworkComponent(networkDBModule: NetworkDBModule) : NetworkComponent

    fun getDatabaseHelper() : DatabaseHelper

}