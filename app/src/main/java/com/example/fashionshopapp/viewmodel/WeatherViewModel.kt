package com.example.fashionshopapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashionshopapp.api.WeatherApiService
import com.example.fashionshopapp.models.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState

    private val weatherApiService = WeatherApiService.create()

    fun fetchCurrentWeather() {
        viewModelScope.launch {
            try {
                val weather = weatherApiService.getCurrentWeather()
                _weatherState.value = weather
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
