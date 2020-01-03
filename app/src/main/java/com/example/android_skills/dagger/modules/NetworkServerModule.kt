package com.example.android_skills.dagger.modules

import com.example.android_skills.dagger.targetClasses.NetworkUtils
import com.example.android_skills.dagger.annotations.DefaultNetwork
import com.example.android_skills.dagger.annotations.UrlNetwork
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class NetworkServerModule {

    @UrlNetwork
    @Provides
    fun provideNetworkUtils(@Named("url")url: String) : NetworkUtils {
        return NetworkUtils(url)
    }

    @DefaultNetwork
    @Provides
    fun provideDefaultUtils() : NetworkUtils {
        return NetworkUtils("default")
    }

    @Named("url")
    @Provides
    fun createUrl() : String {
        return "http:/hello"
    }
}