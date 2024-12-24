package com.example.fashionshopapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.fashionshopapp.models.Product

object ImageSearchHelper {

    private val categoryMap = mapOf(
        1 to "Áo sơ mi",
        2 to "Quần jean",
        3 to "Áo phông",
        4 to "Giày",
        5 to "Váy",
        6 to "Đồ bộ",
        7 to "Áo ấm",
        8 to "Quần short",
        9 to "Tất",
        10 to "Cà vạt",
        11 to "Kẹp cà vạt"
    )

    private fun translateToVietnamese(label: String): String {
        val dictionary = mapOf(
            "Shirt" to "Áo sơ mi",
            "Jeans" to "Quần jean",
            "T-shirt" to "Áo phông",
            "Sneakers" to "Giày",
            "Shoe" to "Giày",
            "Dress" to "Váy",
            "Outfits" to "Đồ bộ",
            "Outerwear" to "Áo ấm",
            "Jacket" to "Áo sơ mi",
            "Shorts" to "Quần short",
            "Socks" to "Tất",
            "Tie" to "Cà vạt",
            "Tie clip" to "Kẹp cà vạt"
        )
        return dictionary[label] ?: label
    }

    fun searchProductsByLabels(
        context: Context,
        products: List<Product>,
        labels: List<String>
    ): List<Product> {
        Log.d("ImageSearchHelper", "Labels before translation: $labels")
        val translatedLabels = labels.map { translateToVietnamese(it) }
        Log.d("ImageSearchHelper", "Translated labels: $translatedLabels")

        var filteredProducts = products.filter { product ->
            val categoryName = categoryMap[product.categoryId] ?: ""
            translatedLabels.any { label -> categoryName.contains(label, ignoreCase = true) }
        }

        if (translatedLabels.contains("Quần jean")) {
            filteredProducts = filteredProducts.filter { product ->
                val categoryName = categoryMap[product.categoryId] ?: ""
                categoryName == "Quần jean" // So khớp chính xác "Quần jean"
            }
        }

        if (filteredProducts.isEmpty()) {
            Toast.makeText(context, "Không tìm thấy sản phẩm phù hợp!", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("ImageSearchHelper", "Filtered products: $filteredProducts")
        }

        return filteredProducts
    }
}