package com.app.weather.domain.repository

import com.app.weather.data.model.WeatherResponseEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getCityWeather(
        cityName: String,
        apiKey: String
    ): Flow<WeatherResponseEntity>
}