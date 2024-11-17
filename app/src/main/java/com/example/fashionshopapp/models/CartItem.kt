package com.example.fashionshopapp.model

import com.example.fashionshopapp.models.Product

data class CartItem(
    val id: String,
    val name: String,
    val price: Double,
    val quantity: Int,
    val product: Product,

)
