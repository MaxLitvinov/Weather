package com.weather.feature.home_page.mapper

import android.content.Context
import com.weather.feature.home_page.model.WeatherModel
import com.weather.foundation.resources.R
import com.weather.service.open_weather_map.domain.model.WeatherDomainModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WeatherDomainModelMapper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val listMapper: DailyDomainModelListMapper,
) {

    fun mapToUiModel(model: WeatherDomainModel, city: String): WeatherModel = with(model) {
        WeatherModel(
            city = city,
            iconUrl = currentWeather.weatherDetails.iconUrl,
            temperature = context.getString(R.string.home_page_temperature_value, currentWeather.temperature),
            weatherDescription = currentWeather.weatherDetails.mainDescription,
            dailyForecasts = listMapper.mapToUiModel(dailyForecasts)
        )
    }
}
