package com.app.weather.data.repository

import com.app.weather.core.utils.parsError
import com.app.weather.data.source.network.NetworkDataSource
import com.app.weather.data.source.network.dto.toEntity
import com.app.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : WeatherRepository {

    override fun getCityWeather(cityName: String, apiKey: String) = flow {
        val cityDto = networkDataSource.getCityWeather(
            cityName = cityName,
            apiKey = apiKey
        )
        if (cityDto != null) {
            emit(cityDto.toEntity())
        } else {
            throw Exception("Not found weather for $cityName")
        }
    }.catch { e ->
        val errorResponse = parsError(e)
        throw Exception(errorResponse.message)
    }.flowOn(Dispatchers.IO)

}