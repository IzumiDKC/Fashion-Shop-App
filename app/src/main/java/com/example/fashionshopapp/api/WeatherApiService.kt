package com.example.fashionshopapp.api

import com.example.fashionshopapp.models.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WeatherApiService {
    @GET("json")
    suspend fun getCurrentWeather(): WeatherResponse

    companion object {
        private const val BASE_URL = "http://ip-api.com/"

        fun create(): WeatherApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(WeatherApiService::class.java)
        }
    }
}
