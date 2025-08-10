package com.app.weather.data.source.network.dto

data class WeatherResponseDto(
    val name: String,
    val main: MainDto,
    val weatherDto: List<WeatherDto>
)

data class MainDto(
    val temp: Float
)

data class WeatherDto(
    val main: String,
    val icon: String
)
