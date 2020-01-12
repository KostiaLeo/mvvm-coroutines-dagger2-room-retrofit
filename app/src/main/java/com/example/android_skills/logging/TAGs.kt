package com.example.android_skills.logging

interface TAGs {
    companion object {
        const val roomTag: String = "Room retrieving"
        const val retrofitTag: String = "Retrofit retrieving"
        const val modelRepositoryTag = "ModelRepository data providing"
        const val viewModelTag = "ViewModelLogging"
        const val fragmentTag = "Fragment dealing"
        const val parentAdapterTag = "Parent adapter"
        const val childAdapterTag = "Child adapter"
    }
}