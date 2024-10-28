package com.taiq.cartmanagement.models

data class CartResponse(
    val sub_categories: List<SubCategory>,
    val products: List<Product>,
    val services: List<Service>,
    val status: String,
    val message: String
)

