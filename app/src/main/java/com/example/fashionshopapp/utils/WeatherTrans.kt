package com.example.fashionshopapp.utils

val weatherDescriptionMap = mapOf(
    "clear sky" to "trời quang",
    "few clouds" to "ít mây",
    "scattered clouds" to "mây rải rác",
    "broken clouds" to "mây đứt quãng",
    "shower rain" to "mưa rào",
    "rain" to "mưa",
    "thunderstorm" to "giông bão",
    "snow" to "tuyết",
    "mist" to "sương mù"
)

fun translateWeatherDescription(description: String): String {
    return weatherDescriptionMap[description] ?: "Không rõ"
}
