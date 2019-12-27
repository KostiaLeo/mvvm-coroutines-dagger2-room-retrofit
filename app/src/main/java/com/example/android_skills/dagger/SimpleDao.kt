package com.example.android_skills.dagger


class SimpleDao(private val urlDao: String) {
    fun showDao(): String = "this is dao - $urlDao"
}