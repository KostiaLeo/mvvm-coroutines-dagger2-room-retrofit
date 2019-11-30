package com.example.restkotlinized.model.pojo

import androidx.databinding.BaseObservable

data class MyNewz(val next: String, val results: Array<Results>)

data class Results(val owner: String, val image: Image, val price_week: String, val emain_count: String, val price_month: String, val phone_count: String,
                   val price: String, val name: String,
                   val category: String, val favourite: String,
                   val view_count: String, val currency: Currency
) : BaseObservable()

data class Image(val width: String, val url: String, val height: String)

data class Currency(val image: String, val name: String, val id: String)