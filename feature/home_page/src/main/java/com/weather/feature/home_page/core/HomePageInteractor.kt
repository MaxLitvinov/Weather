package com.weather.feature.home_page.core

import android.content.Context
import com.weather.feature.home_page.HomePageViewModel
import com.weather.feature.home_page.R
import com.weather.feature.home_page.model.DayForecast
import com.weather.feature.home_page.model.WeatherModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun fetchWeather(): HomePageViewModel.UiState {
        val temperature = 11.5F
        val fakeWeatherModel = WeatherModel(
            "Chernihiv",
            "https://openweathermap.org/img/wn/04d@2x.png",
            context.getString(R.string.home_page_temperature_value, temperature),
            "Overcast clouds",
            listOf(
                DayForecast(1L, "Today", "15°C / 16°C"),
                DayForecast(2L, "Tomorrow", "15°C / 16°C"),
                DayForecast(3L, "15.09 Thursday", "15°C / 16°C"),
                DayForecast(null, "16.09 Friday", "15°C / 16°C"),
                DayForecast(null, "17.09 Saturday", "15°C / 16°C"),
                DayForecast(null, "18.09 Sunday", "15°C / 16°C"),
                DayForecast(null, "19.09 Monday", "15°C / 16°C"),
                DayForecast(null, "20.09 Tuesday", "15°C / 16°C")
            )
        )
        return HomePageViewModel.UiState.Success(fakeWeatherModel)
    }
}
