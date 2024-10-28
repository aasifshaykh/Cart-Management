package com.taiq.cartmanagement.models

data class Product(
    val order_in_out: String,
    val user_id: String,
    val user_full_name: String,
    val user_relation: String,
    val product_id: String,
    val sub_category_id: String,
    val product_name: String,
    val tag_code: String,
    val group_name: String,
    val city_name: String,
    val state_id: String,
    val city_id: String,
    val state_name: String,
    val product_photo: String,
    val product_active_status: String,
    val in_out: String
)

