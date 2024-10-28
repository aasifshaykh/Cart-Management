package com.taiq.cartmanagement.models

data class OrderItem(
    val productName: String,
    val services: List<Service>
)

data class ServiceItem(
    val name: String,
    val amount: Int
)