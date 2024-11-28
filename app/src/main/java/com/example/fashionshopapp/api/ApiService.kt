package com.example.fashionshopapp.api

import com.example.fashionshopapp.models.Brand
import com.example.fashionshopapp.models.Category
import com.example.fashionshopapp.models.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("api/products")
    fun getProducts(): Call<List<Product>>

    @GET("api/brands")
    fun getBrands(): Call<List<Brand>>

    @GET("api/categories")
    fun getCategories(): Call<List<Category>>

    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/auth/register")
    fun register(@Body registerRequest: RegisterRequest): Call<Void>

}
