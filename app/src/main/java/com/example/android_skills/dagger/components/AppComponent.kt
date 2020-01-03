package com.example.android_skills.dagger.components

import com.example.android_skills.dagger.targetClasses.DatabaseHelper
import com.example.android_skills.dagger.modules.NetworkDBModule
import com.example.android_skills.dagger.modules.StorageModule
import dagger.Binds
import dagger.BindsInstance
import dagger.Component

@Component(modules = [StorageModule::class])
interface AppComponent {
    /** inject-method
    fun injectRepository(repository: Repository)
    */

    /** get-methods */

    @Component.Builder
    interface MyBuilder {
        fun buildTheComponent() : AppComponent

        @BindsInstance
        fun setStringDaoToNetwork(dao: String): MyBuilder
    }

    fun createNetworkComponent(networkDBModule: NetworkDBModule) : NetworkComponent

    fun getDatabaseHelper() : DatabaseHelper

}