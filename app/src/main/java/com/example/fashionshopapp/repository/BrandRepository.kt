package com.example.fashionshopapp.repository

import com.example.fashionshopapp.api.RetrofitInstance
import com.example.fashionshopapp.models.Brand
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BrandRepository {
    suspend fun fetchBrands(): List<Brand> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getBrands().execute()
                response.body() ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}