package com.app.weather

import com.app.weather.data.model.MainEntity
import com.app.weather.data.model.WeatherEntity
import com.app.weather.data.model.WeatherResponseEntity
import com.app.weather.data.repository.WeatherRepositoryImp
import com.app.weather.data.source.network.NetworkDataSource
import com.app.weather.data.source.network.dto.MainDto
import com.app.weather.data.source.network.dto.WeatherDto
import com.app.weather.data.source.network.dto.WeatherResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImpTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var networkDataSource: NetworkDataSource
    private lateinit var repository: WeatherRepositoryImp

    val sampleWeatherDto = WeatherDto(main = "Cloudy", icon = "04d")
    val sampleMainDto = MainDto(temp = 20.5f)
    val sampleWeatherResponseDto = WeatherResponseDto(
        name = "London",
        main = sampleMainDto,
        weatherDto = listOf(sampleWeatherDto)
    )

    val expectedEntity = WeatherResponseEntity(
        name = "London",
        mainEntity = MainEntity(temp = 20.5f),
        weatherEntity = listOf(WeatherEntity(main = "Cloudy", icon = "04d"))
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        networkDataSource = mock()
        repository = WeatherRepositoryImp(networkDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCityWeather emits correct entity`() = runTest {
        whenever(
            networkDataSource.getCityWeather(
                "London", "apiKey"
            )
        ).thenReturn(
            sampleWeatherResponseDto
        )

        val result = repository.getCityWeather("London", "apiKey").first()

        assertEquals(expectedEntity, result)
    }

    @Test
    fun `getCityWeather throws exception when null returned`() = runTest {
        whenever(
            networkDataSource.getCityWeather(
                "UnknownCity", "apiKey"
            )
        ).thenReturn(null)

        val exception = assertFailsWith<Exception> {
            repository.getCityWeather("UnknownCity", "apiKey").collect()
        }

        assertEquals(
            "An unexpected error occurred: Not found weather for UnknownCity",
            exception.message
        )
    }
}
