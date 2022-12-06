package com.weather.feature.home_page.core

import android.content.Context
import com.weather.feature.home_page.HomePageViewModel
import com.weather.feature.home_page.mapper.WeatherDomainModelMapper
import com.weather.foundation.resources.R
import com.weather.service.ip_api.domain.model.IpDomainResult
import com.weather.service.ip_api.domain.repository.IpRepository
import com.weather.service.open_weather_map.domain.model.WeatherDomainResult
import com.weather.service.open_weather_map.domain.repository.WeatherForecastRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ipRepository: IpRepository,
    private val weatherForecastRepository: WeatherForecastRepository,
    private val mapper: WeatherDomainModelMapper,
) {

    suspend fun fetchWeather(): HomePageViewModel.UiState = handleLocation()

    private suspend fun handleLocation(): HomePageViewModel.UiState =
        when (val ipDomainResult = ipRepository.fetchLocation()) {
            is IpDomainResult.Success -> handleWeather(ipDomainResult)
            is IpDomainResult.Failure -> failure(ipDomainResult.error)
        }

    private fun failure(errorMessage: String?) = HomePageViewModel.UiState.Failure(
        errorMessage ?: context.getString(R.string.something_went_wrong)
    )

    private suspend fun handleWeather(
        ipDomainResult: IpDomainResult.Success,
    ): HomePageViewModel.UiState = with(ipDomainResult) {
        when (val weatherDomainResult = weatherForecastRepository.fetchWeather("$lat", "$lon")) {
            is WeatherDomainResult.Success -> {
                val weatherDomainModel = weatherDomainResult.model
                val weatherUiModel = mapper.mapToUiModel(weatherDomainModel, city)
                HomePageViewModel.UiState.Success(weatherUiModel)
            }
            is WeatherDomainResult.Failure -> failure(weatherDomainResult.error)
        }
    }
}
