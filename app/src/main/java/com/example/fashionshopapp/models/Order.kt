package com.example.fashionshopapp.models

data class Order(
    val id: Int,
    val orderDate: String,
    val totalPrice: Double,
    val shippingAddress: String,
    val status: String,
    val notes: String?,
    val orderDetails: List<OrderDetail>
)

data class OrderDetail(
    val name: String,
    val originalPrice: Double,
    val finalPrice: Double,
    val quantity: Int
)

