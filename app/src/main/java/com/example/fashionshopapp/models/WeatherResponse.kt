package com.example.fashionshopapp.models


data class WeatherResponse(
    val city: String,
    val regionName: String,
    val country: String,
    val lat: Double,
    val lon: Double,
)
