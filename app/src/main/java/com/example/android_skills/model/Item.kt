package com.example.android_skills.model

data class ItemResponse (
    val itemList: List<Item>
)

data class Item(
    val click_url: String,
    val img: String,
    val time: String,
    val title: String,
    val top: String,
    val type: String
)