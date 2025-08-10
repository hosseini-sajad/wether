package com.app.weather.data.source.network

import javax.inject.Inject

class NetworkDataSourceImp @Inject constructor(
    private val apiService: ApiService
) : NetworkDataSource {
    override suspend fun getCityWeather(cityName: String, apiKey: String) =
        apiService.getWeather(city = cityName, apiKey = apiKey)
}