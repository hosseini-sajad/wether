package com.app.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.core.utils.parsError
import com.app.weather.data.model.WeatherResponseEntity
import com.app.weather.domain.usecase.GetWeatherOfCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherOfCityUseCase: GetWeatherOfCityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    fun fetchWeatherOfCity(
        cityName: String,
        apiKey: String
    ) {
        getWeatherOfCityUseCase(cityName, apiKey)
            .onStart {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            }
            .onEach { weather ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        weather = weather,
                        errorMessage = null
                    )
                }
            }
            .catch { exception ->
                val errorResponse = parsError(exception)
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = errorResponse.message)
                }
            }
            .flowOn(IO)
            .launchIn(viewModelScope)
    }

}


data class HomeUiState(
    val weather: WeatherResponseEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)