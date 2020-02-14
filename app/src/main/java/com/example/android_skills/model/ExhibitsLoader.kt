package com.example.android_skills.model

interface ExhibitsLoader {
    suspend fun getExhibitList(): List<Exhibit>
}