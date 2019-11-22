package com.example.restkotlinized.model.pojo

data class Results(val owner: String, val image: Image, val price_week: String, val emain_count: String, val price_month: String, val phone_count: String,
                   val price: String, val name: String,
                   val category: String, val favourite: String,
                   val view_count: String, val currency: Currency
){
    override fun toString(): String {
        return super.toString()
    }
}
