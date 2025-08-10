package com.app.weather.data.source.network

import com.app.weather.data.source.network.dto.WeatherResponseDto

interface NetworkDataSource {
    suspend fun getCityWeather(
        cityName: String,
        apiKey: String
    ): WeatherResponseDto?
}