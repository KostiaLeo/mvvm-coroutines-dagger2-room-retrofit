package com.example.android_skills.model

interface Source {
    fun retrieveData(): List<Exhibit>
}

interface LocalSource : Source {
    fun saveData(exhibits: List<Exhibit>)
}

interface RemoteSource : Source