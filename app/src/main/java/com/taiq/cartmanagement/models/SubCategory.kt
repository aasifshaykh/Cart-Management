package com.taiq.cartmanagement.models

data class SubCategory(
    val sub_category_id: String,
    val service_ids: String,
    val regular_prices: String,
    val parent_category_id: String,
    val sub_category_name: String,
    val sub_category_photo: String,
    val sub_category_active_status: String,

)
