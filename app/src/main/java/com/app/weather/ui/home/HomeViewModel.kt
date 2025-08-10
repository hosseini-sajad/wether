package com.app.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.core.utils.parsError
import com.app.weather.data.model.WeatherResponseEntity
import com.app.weather.domain.usecase.GetWeatherOfCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherOfCityUseCase: GetWeatherOfCityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    private fun getWeatherOfCity(
        cityName: String,
        apiKey: String
    ) {
        viewModelScope.launch {
            try {
                val weather = getWeatherOfCityUseCase.invoke(
                    cityName = cityName,
                    apiKey = apiKey
                ).catch { exception ->
                    val errorResponse = parsError(exception)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = errorResponse.message
                    )
                }
                _uiState.value = HomeUiState(
                    weather = weather,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Failed to fetch weather"
                )
            }
        }
    }

}


data class HomeUiState(
    val weather: Flow<WeatherResponseEntity?> = emptyFlow(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)