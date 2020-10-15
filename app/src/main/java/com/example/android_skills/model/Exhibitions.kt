package com.example.android_skills.model


data class Exhibitions(
    val list: List<Exhibit>
)

data class Exhibit(
    val images: List<String>,
    val title: String
)