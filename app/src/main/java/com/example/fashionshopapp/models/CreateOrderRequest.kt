package com.example.fashionshopapp.models

data class CreateOrderRequest(
    val userId: String,
    val totalPrice: Double,
    val shippingAddress: String,
    val notes: String? = null
)
