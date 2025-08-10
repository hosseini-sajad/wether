package com.app.weather.data.source.network.dto

data class WeatherDto(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Float
)

data class Weather(
    val main: String,
    val icon: String
)
