package com.app.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.weather.core.Constants
import com.app.weather.ui.home.HomeScreen
import com.app.weather.ui.home.HomeViewModel
import com.app.weather.ui.theme.MiniWeatherDashboardAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniWeatherDashboardAppTheme {
                WeatherApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = hiltViewModel()

    val uiState by homeViewModel.uiState.collectAsState()
    var cityName by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Weather App") }) }
    ) { innerPadding ->
        HomeScreen(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            cityName = cityName,
            onCityNameChange = { cityName = it },
            onSearch = {
                homeViewModel.fetchWeatherOfCity(cityName, Constants.WEATHER_API_KEY)
            },
            onRetry = {
                homeViewModel.fetchWeatherOfCity(cityName, Constants.WEATHER_API_KEY)
            }
        )
    }
}