package com.example.fashionshopapp.repository

import com.example.fashionshopapp.api.RetrofitInstance
import com.example.fashionshopapp.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository {
    suspend fun fetchCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getCategories().execute()
                response.body() ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}