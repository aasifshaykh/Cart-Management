package com.taiq.cartmanagement.models

data class Service(
    val service_id: String,
    val service_name: String,
    val service_desc: String,
    val indexing: String,
    val included_service: String,
    val included_service_name: String,
    val service_base_price: String,
    val service_icon: String,
    val service_active_status: String,
    var isSelected: Boolean = false
)

