package com.example.fashionshopapp.models

data class Order(
    val id: Int,
    val userId: String,
    val orderDate: String,
    val totalPrice: Double,
    val shippingAddress: String?,
    val notes: String?,
    val status: String
)