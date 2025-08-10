package com.app.weather.domain.usecase

import com.app.weather.data.model.WeatherResponseEntity
import com.app.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherOfCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        cityName: String,
        apiKey: String
    ): Flow<WeatherResponseEntity> {
        return weatherRepository.getCityWeather(
            cityName = cityName,
            apiKey = apiKey
        )
    }
}