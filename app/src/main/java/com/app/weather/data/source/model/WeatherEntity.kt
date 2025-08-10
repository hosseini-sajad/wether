package com.app.weather.data.source.model

data class WeatherResponseEntity(
    val name: String,
    val mainEntity: MainEntity,
    val weatherEntity: List<WeatherEntity>
)

data class MainEntity(
    val temp: Float
)

data class WeatherEntity(
    val main: String,
    val icon: String
)
