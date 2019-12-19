package com.example.restkotlinized.model.pojo

data class Films(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)