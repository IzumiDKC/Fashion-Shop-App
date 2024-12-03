package com.example.fashionshopapp.screens

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.*
import com.example.fashionshopapp.R
import com.example.fashionshopapp.utils.translateWeatherDescription
import com.example.fashionshopapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val weatherState by viewModel.weatherState.collectAsState()
    val weatherDetails by viewModel.weatherDetails.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentWeatherAndDetails("0960e4a5c0a7f890a664fbd6a4e4ed70")
    }

    if (weatherDetails != null) {
        val temperature = weatherDetails!!.main.temp
        val animationRes = when {
            temperature < 15 -> R.raw.cold_animation
            temperature in 15.0..25.0 -> R.raw.cloudy_animation
            else -> R.raw.sunny_animation
        }

        val gradientColors = when {
            temperature < 15 -> listOf(Color(0xFF1E3C72), Color(0xFF2A5298))
            temperature in 15.0..25.0 -> listOf(Color(0xFF56CCF2), Color(0xFF2F80ED))
            else -> listOf(Color(0xFFFF8008), Color(0xFFFFC837))
        }

        WeatherContent(
            city = weatherState!!.city,
            regionName = weatherState!!.regionName,
            temperature = temperature,
            weatherDescription = translateWeatherDescription(weatherDetails!!.weather[0].description),
            animationRes = animationRes,
            gradientColors = gradientColors
        )
    } else {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun WeatherContent(
    city: String,
    regionName: String,
    temperature: Double,
    weatherDescription: String,
    @RawRes animationRes: Int,
    gradientColors: List<Color>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Vị trí hiện tại: $city", // $regionName
                style = MaterialTheme.typography.h4,
                color = Color.White
            )
            Text(
                text = "Nhiệt độ: ${temperature}°C",
                style = MaterialTheme.typography.h2,
                color = Color.White
            )
            Text(
                text = "Thời tiết: $weatherDescription",
                style = MaterialTheme.typography.h5,
                color = Color.White
            )


            val suggestion = when {
                temperature < 15 -> "Hãy mặc áo khoác dày!"
                temperature in 15.0..28.0 -> "Mặc áo khoác nhẹ hoặc áo dài tay."
                else -> "Mặc áo ngắn tay hoặc quần short."
            }
            Text(
                text = "Lơi khuyên: $suggestion",
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
            LottieAnimation(
                composition = rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes)).value,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
