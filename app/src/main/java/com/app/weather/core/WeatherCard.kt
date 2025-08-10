package com.app.weather.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.app.weather.data.model.MainEntity
import com.app.weather.data.model.WeatherEntity
import com.app.weather.data.model.WeatherResponseEntity
import com.app.weather.ui.theme.MiniWeatherDashboardAppTheme

@Composable
fun WeatherCard(weather: WeatherResponseEntity?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weather?.weatherEntity[0]?.main ?: "error",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "${weather?.mainEntity?.temp?.toInt() ?: 0}°C",
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = weather?.name ?: "",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(8.dp))
            val iconCode = weather?.weatherEntity[0]?.icon
            if (!iconCode.isNullOrEmpty()) {
                AsyncImage(
                    model = "https://openweathermap.org/img/wn/${iconCode}@2x.png",
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun WeatherCardPreview() {
    val dummyWeather = WeatherResponseEntity(
        name = "New York",
        mainEntity = MainEntity(
            temp = 25.5f
        ),
        weatherEntity = listOf(
            WeatherEntity(
                main = "Cloudy",
                icon = "04d"
            )
        )
    )
    MiniWeatherDashboardAppTheme {
        WeatherCard(
            weather = dummyWeather
        )
    }
}