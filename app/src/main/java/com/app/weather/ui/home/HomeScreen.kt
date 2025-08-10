package com.app.weather.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.weather.core.WeatherCard
import com.app.weather.data.model.MainEntity
import com.app.weather.data.model.WeatherEntity
import com.app.weather.data.model.WeatherResponseEntity
import com.app.weather.ui.theme.MiniWeatherDashboardAppTheme

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    cityName: String,
    onCityNameChange: (String) -> Unit,
    onSearch: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = cityName,
                onValueChange = onCityNameChange,
                label = { Text("City") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onSearch,
                modifier = Modifier.fillMaxWidth(),
                enabled = cityName.isNotBlank()
            ) {
                Text("Search")
            }

            Spacer(Modifier.height(24.dp))

            when {
                uiState.errorMessage != null -> {
                    ErrorCard(
                        message = uiState.errorMessage,
                        onRetry = onRetry
                    )
                }

                uiState.weather != null -> {
                    WeatherCard(uiState.weather)
                }
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun ErrorCard(message: String?, onRetry: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message ?: "Error occurred",
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}


val mockWeather = WeatherResponseEntity(
    name = "New York",
    mainEntity = MainEntity(temp = 25.0f),
    weatherEntity = listOf(
        WeatherEntity(main = "Clear", icon = "01d")
    )
)

@Preview(showBackground = true, name = "Loading State")
@Composable
fun HomeScreenLoadingPreview() {
    MiniWeatherDashboardAppTheme {
        HomeScreen(
            uiState = HomeUiState(isLoading = true),
            cityName = "",
            onCityNameChange = {},
            onSearch = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun HomeScreenErrorPreview() {
    MiniWeatherDashboardAppTheme {
        HomeScreen(
            uiState = HomeUiState(errorMessage = "Network error occurred"),
            cityName = "London",
            onCityNameChange = {},
            onSearch = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "Success State")
@Composable
fun HomeScreenSuccessPreview() {
    MiniWeatherDashboardAppTheme {
        HomeScreen(
            uiState = HomeUiState(weather = mockWeather),
            cityName = "New York",
            onCityNameChange = {},
            onSearch = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
fun HomeScreenEmptyPreview() {
    MiniWeatherDashboardAppTheme {
        HomeScreen(
            uiState = HomeUiState(),
            cityName = "",
            onCityNameChange = {},
            onSearch = {},
            onRetry = {}
        )
    }
}