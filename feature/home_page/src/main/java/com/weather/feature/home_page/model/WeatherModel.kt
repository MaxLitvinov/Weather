package com.weather.feature.home_page.model

data class WeatherModel(
    var city: String,
    var iconUrl: String,
    var temperature: String,
    var weatherDescription: String,
    var dailyForecasts: List<DayForecast>
)

data class DayForecast(
    val id: Long?,
    var dayName: String,
    var dayNightTemperature: String
)
