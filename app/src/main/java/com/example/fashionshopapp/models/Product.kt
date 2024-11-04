package com.example.fashionshopapp.models

data class Product(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val isHot: Boolean,
    val promotionPrice: Double?,
    val hotStartDate: String?,
    val hotEndDate: String?
)
