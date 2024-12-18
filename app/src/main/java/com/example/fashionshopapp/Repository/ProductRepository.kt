package com.example.fashionshopapp.Repository

import com.example.fashionshopapp.api.RetrofitInstance
import com.example.fashionshopapp.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository {
    suspend fun fetchProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getProducts().execute()
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}
