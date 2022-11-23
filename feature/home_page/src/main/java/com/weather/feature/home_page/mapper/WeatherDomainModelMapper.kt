package com.weather.feature.home_page.mapper

import com.weather.feature.home_page.model.DayForecast
import com.weather.feature.home_page.model.WeatherModel
import javax.inject.Inject

class WeatherDomainModelMapper @Inject constructor(
    private val dailyDomainModelMapper: DailyDomainModelMapper
) {

    // TODO: Stub WeatherModel model
    fun mapToUiModel(domainModel: Any) =
        WeatherModel(
            city = "Chernihiv",
            iconUrl = "https://openweathermap.org/img/wn/04d@2x.png",
            temperature = "11.5",
            weatherDescription = "Overcast clouds",
            dailyForecasts = listOf(
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
}
