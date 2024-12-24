package com.example.fashionshopapp.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://dacb-14-186-75-52.ngrok-free.app/"

    var token: String? = null

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder().apply {
            token?.let {
                Log.d("AUTH_INTERCEPTOR", "Adding Authorization header with token: Bearer $it")
                addHeader("Authorization", "Bearer $it")
            } ?: Log.d("AUTH_INTERCEPTOR", "Token is null, skipping Authorization header")
        }.build()
        val authorizationHeader = newRequest.header("Authorization")
        Log.d("AUTH_INTERCEPTOR", "Final request headers: $authorizationHeader")
        chain.proceed(newRequest)

    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.NONE
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            val responseBody = response.peekBody(Long.MAX_VALUE)
            Log.d("API_CALL", "Request: ${request.url}, Response: ${responseBody.string()}")

            response
        }
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
/*

package com.example.fashionshopapp.api

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://1ff6-2402-800-6318-920d-1566-804e-ea8e-69d4.ngrok-free.app/"
    private const val TOKEN_KEY = "TOKEN_KEY"

    // Biến token toàn cục
    var token: String? = null
        private set

    // Phương thức để lưu token vào SharedPreferences
    fun saveToken(context: Context, newToken: String?) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(TOKEN_KEY, newToken).apply()
        token = newToken
        Log.d("RetrofitInstance", "Token saved: $token")
    }

    // Phương thức để lấy token từ SharedPreferences
    fun loadToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        token = sharedPreferences.getString(TOKEN_KEY, null)
        Log.d("RetrofitInstance", "Token loaded: $token")
    }

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder().apply {
            // Nếu token không null, thêm token vào header Authorization
            token?.let {
                Log.d("AUTH_INTERCEPTOR", "Adding Authorization header with token: Bearer $it")
                addHeader("Authorization", "Bearer $it")
            } ?: Log.d("AUTH_INTERCEPTOR", "Token is null, skipping Authorization header")
        }.build()
        val authorizationHeader = newRequest.header("Authorization")
        Log.d("AUTH_INTERCEPTOR", "Final request headers: $authorizationHeader")
        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            val responseBody = response.peekBody(Long.MAX_VALUE)
            Log.d("API_CALL", "Request: ${request.url}, Response: ${responseBody.string()}")

            response
        }
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

 */
