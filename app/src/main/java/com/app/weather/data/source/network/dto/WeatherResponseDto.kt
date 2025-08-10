package com.app.weather.data.source.network.dto

import com.app.weather.data.model.MainEntity
import com.app.weather.data.model.WeatherEntity
import com.app.weather.data.model.WeatherResponseEntity
import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("main")
    val main: MainDto,
    @SerializedName("weather")
    val weatherDto: List<WeatherDto>
)

data class MainDto(
    val temp: Float
)

data class WeatherDto(
    val main: String,
    val icon: String
)

fun WeatherResponseDto.toEntity() = WeatherResponseEntity(
    name = name,
    mainEntity = main.toEntity(),
    weatherEntity = weatherDto.map { it.toEntity() }
)


fun MainDto.toEntity() = MainEntity(
    temp = temp
)


fun WeatherDto.toEntity() = WeatherEntity(
    main = main,
    icon = icon
)
