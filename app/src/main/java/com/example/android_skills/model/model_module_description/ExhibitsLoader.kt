package com.example.android_skills.model.model_module_description

interface ExhibitsLoader{
    suspend fun getExhibitList(): List<Exhibit>
}