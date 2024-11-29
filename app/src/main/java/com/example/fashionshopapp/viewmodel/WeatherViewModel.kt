package com.example.fashionshopapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.api.WeatherApiService
import com.example.fashionshopapp.api.WeatherDetailsApiService
import com.example.fashionshopapp.models.WeatherDetailsResponse
import com.example.fashionshopapp.models.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState

    private val _weatherDetails = MutableStateFlow<WeatherDetailsResponse?>(null)
    val weatherDetails: StateFlow<WeatherDetailsResponse?> = _weatherDetails

    private val weatherApiService = WeatherApiService.create()
    private val weatherDetailsApiService = WeatherDetailsApiService.create()

    fun fetchCurrentWeatherAndDetails(apiKey: String) {
        viewModelScope.launch {
            try {
                // Lấy thông tin vị trí
                val location = weatherApiService.getCurrentWeather()
                _weatherState.value = location

                // Lấy thông tin thời tiết từ OpenWeather
                val details = weatherDetailsApiService.getWeatherDetails(
                    lat = location.lat,
                    lon = location.lon,
                    apiKey = apiKey
                )
                _weatherDetails.value = details
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

