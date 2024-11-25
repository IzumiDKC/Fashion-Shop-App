package com.example.fashionshopapp.api

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://a786-2402-800-6319-60fd-783c-1ddd-6ba7-7f5e.ngrok-free.app/"

    val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        val responseBody = response.peekBody(Long.MAX_VALUE)
        Log.d("API_CALL", "Request: ${request.url}, Response: ${responseBody.string()}")
        response
    }.build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
