package com.example.android_skills.model

interface Source {
    suspend fun retrieveData(): List<Exhibit>
}

interface LocalSource : Source {
    suspend fun refreshData(exhibits: List<Exhibit>)
}

interface RemoteSource : Source