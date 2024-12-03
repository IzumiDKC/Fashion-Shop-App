package com.example.fashionshopapp.api

import com.example.fashionshopapp.models.Brand
import com.example.fashionshopapp.models.Category
import com.example.fashionshopapp.models.CreateOrderRequest
import com.example.fashionshopapp.models.Order
import com.example.fashionshopapp.models.Product
import com.example.fashionshopapp.models.UpdateProfileRequest
import com.example.fashionshopapp.models.UserProfile
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @POST("api/orders/create")
    fun createOrder(@Body request: CreateOrderRequest): Response<Order>

    @GET("api/account/profile/{userId}")
    suspend fun getProfile(@Path("userId") userId: String): Response<UserProfile>

    @PUT("api/account/update-profile/{userId}")
    suspend fun updateProfile(
        @Path("userId") userId: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ): Response<Void>


}
