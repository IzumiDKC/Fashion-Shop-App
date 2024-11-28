package com.example.fashionshopapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fashionshopapp.utils.AppBackground
import com.example.fashionshopapp.viewmodel.WeatherViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val weatherState by viewModel.weatherState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentWeather()
    }

    AppBackground {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (weatherState != null) {
                Text(text = "Thành phố: ${weatherState!!.city}", style = MaterialTheme.typography.h6)
                Text(text = "Khu vực: ${weatherState!!.regionName}")
                Text(text = "Quốc gia: ${weatherState!!.country}")
                Text(text = "Vĩ độ: ${weatherState!!.lat}, Kinh độ: ${weatherState!!.lon}")
            } else {
                CircularProgressIndicator()
            }
        }
    }
}
