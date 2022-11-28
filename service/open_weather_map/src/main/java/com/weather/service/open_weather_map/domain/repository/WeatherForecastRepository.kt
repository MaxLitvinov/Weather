package com.weather.service.open_weather_map.domain.repository

import com.weather.service.api_client.network_response.NetworkResponse
import com.weather.service.logger.Logger
import com.weather.service.open_weather_map.data.api.OpenWeatherMapApi
import com.weather.service.open_weather_map.data.dto.WeatherDto
import com.weather.service.open_weather_map.domain.mapper.WeatherDtoMapper
import com.weather.service.open_weather_map.domain.model.WeatherDomainResult
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(
    private val api: OpenWeatherMapApi,
    private val mapper: WeatherDtoMapper,
    private val logger: Logger
) {

    suspend fun fetchWeather(lat: String, lon: String): WeatherDomainResult =
        when (val response: NetworkResponse<WeatherDto, Any> = api.fetchWeather(lat, lon)) {
            is NetworkResponse.Success ->
                WeatherDomainResult.Success(mapper.mapToDomainModel(response.body))
            is NetworkResponse.ServerError ->
                handleError("Server error code: ${response.code}, message: ${response.error?.message}")
            is NetworkResponse.NetworkError ->
                handleError("Network error body - ${response.body}, message - ${response.error.message}")
            is NetworkResponse.UnknownError ->
                handleError("Unknown error code - ${response.code}")
        }

    private fun handleError(errorMessage: String): WeatherDomainResult.Failure {
        logger.report(errorMessage)
        return WeatherDomainResult.Failure(errorMessage)
    }
}
