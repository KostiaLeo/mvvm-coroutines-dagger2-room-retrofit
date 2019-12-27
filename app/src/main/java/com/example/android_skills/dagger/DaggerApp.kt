package com.example.android_skills.dagger

import com.example.android_skills.dagger.components.AppComponent
import com.example.android_skills.dagger.components.DaggerAppComponent
import com.example.android_skills.dagger.components.NetworkComponent
import com.example.android_skills.dagger.modules.NetworkDBModule

class DaggerApp {
    companion object {
        val component: AppComponent = DaggerAppComponent.create()
        val networkComponent: NetworkComponent = DaggerApp.component.createNetworkComponent(NetworkDBModule("myDaoLink"))
    }
}