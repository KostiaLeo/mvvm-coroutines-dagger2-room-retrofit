package com.example.android_skills.dagger.modules

import com.example.android_skills.dagger.NetworkUtils
import com.example.android_skills.dagger.annotations.DefaultNetwork
import com.example.android_skills.dagger.annotations.UrlNetwork
import dagger.Module
import dagger.Provides

@Module
class NetworkServerModule {

    @UrlNetwork
    @Provides
    fun provideNetworkUtils(url: String) : NetworkUtils {
        return NetworkUtils(url)
    }

    @DefaultNetwork
    @Provides
    fun provideDefaultUtils() : NetworkUtils {
        return NetworkUtils("default")
    }

    @Provides
    fun createUrl() : String {
        return "http:/hello"
    }
}