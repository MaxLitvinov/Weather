package com.weather.feature.home_page.mapper

import com.weather.feature.home_page.model.DayForecast
import javax.inject.Inject

class DailyDomainModelMapper @Inject constructor() {

    // TODO: Stub DayForecast model
    fun mapToUiModel(domainModel: Any) =
        DayForecast(
            id = 1L,
            dayName = "Today",
            dayNightTemperature = "15°C / 16°C"
        )
}
