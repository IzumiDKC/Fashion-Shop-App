package com.example.fashionshopapp.api

import com.example.fashionshopapp.models.Brand
import com.example.fashionshopapp.models.Category
import com.example.fashionshopapp.models.CreateOrderRequest
import com.example.fashionshopapp.models.Order
import com.example.fashionshopapp.models.Product
import com.example.fashionshopapp.models.UpdatedProfileModel
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

    @GET("api/account/profile/{userId}")
    suspend fun getProfile(@Path("userId") userId: String): Response<UserProfile>

    @PUT("api/account/update-profile/{userId}")
    suspend fun updateProfile(
        @Path("userId") userId: String,
        @Body updatedProfile: UpdatedProfileModel
    ): Response<Void>

    @GET("onsale")
    fun getProductsOnSale(): Call<List<Product>>

    @GET("hot-products")
    fun getHotProducts(): Call<List<Product>>

    @GET("hot-products-time-remaining")
    fun getFlashSaleProducts(): Call<List<Product>>

    @GET("christmas-collection")
    fun getChristmasCollection(): Call<List<Product>>

    @GET("accessories")
    fun getAccessory(): Call<List<Product>>

    @GET("new-arrival")
    fun getNewArrival(): Call<List<Product>>

    @GET("user-orders/{userId}")
    suspend fun getUserOrders(@Path("userId") userId: String): Response<List<Order>>



}
