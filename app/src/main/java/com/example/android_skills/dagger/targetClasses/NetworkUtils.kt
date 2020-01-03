package com.example.android_skills.dagger.targetClasses

class NetworkUtils(val url: String) {
    fun showUrl() : String = "$url is your url"
}