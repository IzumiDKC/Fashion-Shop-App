package com.example.fashionshopapp.api

import com.example.fashionshopapp.models.Brand
import com.example.fashionshopapp.models.Category
import com.example.fashionshopapp.models.Product
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/products")
    fun getProducts(): Call<List<Product>>

    @GET("api/brands")
    fun getBrands(): Call<List<Brand>>

    @GET("api/categories")
    fun getCategories(): Call<List<Category>>
}
