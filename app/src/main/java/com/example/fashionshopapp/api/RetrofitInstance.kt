package com.example.fashionshopapp.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://fa94-2001-ee0-192-4d53-5d10-c65-cb6e-3a13.ngrok-free.app/"

    var token: String? = null

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder().apply {
            // Nếu token không null, thêm token vào header Authorization
            RetrofitInstance.token?.let {
                Log.d("AUTH_INTERCEPTOR", "Adding Authorization header with token: Bearer $it")
                addHeader("Authorization", "Bearer $it")
            } ?: Log.d("AUTH_INTERCEPTOR", "Token is null, skipping Authorization header")
        }.build()
        val authorizationHeader = newRequest.header("Authorization")
        Log.d("AUTH_INTERCEPTOR", "Final request headers: $authorizationHeader")
        chain.proceed(newRequest)

    }


    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("HTTP_LOG", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.HEADERS // Chú ý: Log headers
    }


    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        /*.addInterceptor { chain ->

            val request = chain.request()
            val response = chain.proceed(request)

            val responseBody = response.peekBody(Long.MAX_VALUE)
            Log.d("API_CALL", "Request: ${request.url}, Response: ${responseBody.string()}")

            response
        }*/
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}

