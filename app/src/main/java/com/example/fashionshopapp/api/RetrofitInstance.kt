package com.example.fashionshopapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://f643-2402-800-6319-60fd-91e3-f473-8233-539e.ngrok-free.app/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Cấu hình OkHttpClient
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            val responseBody = response.peekBody(Long.MAX_VALUE)
            Log.d("API_CALL", "Request: ${request.url}, Response: ${responseBody.string()}")

            response
        }
        .connectTimeout(30, TimeUnit.SECONDS) // Timeout kết nối
        .readTimeout(30, TimeUnit.SECONDS)    // Timeout đọc
        .writeTimeout(30, TimeUnit.SECONDS)   // Timeout ghi
        .build()

    // Khởi tạo Retrofit instance
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
