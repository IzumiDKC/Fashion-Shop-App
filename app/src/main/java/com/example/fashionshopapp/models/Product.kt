package com.example.fashionshopapp.models

data class Product(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val isHot: Boolean,
    val promotionPrice: Double?,
    val hotStartDate: String?,
    val hotEndDate: String?,
    val brandId: Int,
    val categoryId: Int
) {
    val finalPrice: Double
        get() {
            return if (promotionPrice != null && promotionPrice > 0) {
                price - (price * (promotionPrice / 100))
            } else {
                price
            }
        }
}

