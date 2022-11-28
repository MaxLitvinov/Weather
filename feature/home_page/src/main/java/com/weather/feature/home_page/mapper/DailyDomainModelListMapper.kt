package com.weather.feature.home_page.mapper

import android.content.Context
import com.weather.feature.home_page.model.DayForecast
import com.weather.foundation.resources.R
import com.weather.library.time.TimestampProvider
import com.weather.service.open_weather_map.domain.model.DailyDomainModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DailyDomainModelListMapper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val timestampProvider: TimestampProvider,
) {

    fun mapToUiModel(domainList: List<DailyDomainModel>): List<DayForecast> {
        val list = mutableListOf<DayForecast>()
        getDayNightTemperatureMaxLengths(domainList).let { (dayTempMaxLength, nightTempMaxLength) ->
            domainList.map { dailyDomainModel ->
                val dayNightTemp = context.getString(
                    R.string.home_page_day_night_temperature_values,
                    getDayTemperature(dailyDomainModel.temperature.day, dayTempMaxLength),
                    getNightTemperature(dailyDomainModel.temperature.night, nightTempMaxLength)
                )
                list.add(
                    DayForecast(
                        id = dailyDomainModel.generateId(),
                        dayName = dailyDomainModel.generateDayName(),
                        dayNightTemperature = dayNightTemp
                    )
                )
            }
        }
        return list
    }

    private fun DailyDomainModel.generateId(): Long? =
        hourlyForecasts?.let { time }

    private fun DailyDomainModel.generateDayName(): String =
        timestampProvider.toDayMonthAndDayName(time)

    private fun getDayNightTemperatureMaxLengths(dailyForecasts: List<DailyDomainModel>): Pair<Int, Int> {
        var dayTempMaxStringLength = 0
        var nightTempMaxStringLength = 0
        dailyForecasts.map { dailyDomainModel ->
            val dayTempLength = dailyDomainModel.temperature.day.toString().length
            dayTempMaxStringLength = Math.max(dayTempMaxStringLength, dayTempLength)

            val nightTempLength = dailyDomainModel.temperature.night.toString().length
            nightTempMaxStringLength = Math.max(dayTempMaxStringLength, nightTempLength)
        }
        return Pair(dayTempMaxStringLength, nightTempMaxStringLength)
    }

    private fun getDayTemperature(temperature: Float, maxStringLength: Int): String {
        val currentDayTempLength = temperature.toString().length
        var dayValue: String = context.getString(R.string.home_page_temperature_value, temperature)
        if (currentDayTempLength < maxStringLength) {
            val lengthDifference = maxStringLength - currentDayTempLength
            for (spaceCount in 0..lengthDifference) {
                dayValue += " "
            }
        }
        return dayValue
    }

    private fun getNightTemperature(temperature: Float, maxStringLength: Int): String {
        val currentNightTempLength = temperature.toString().length
        var nightValue: String =
            context.getString(R.string.home_page_temperature_value, temperature)
        if (currentNightTempLength < maxStringLength) {
            val lengthDifference = maxStringLength - currentNightTempLength
            var spaces = ""
            for (spaceCount in 0..lengthDifference) {
                spaces += " "
            }
            nightValue = spaces + nightValue
        }
        return nightValue
    }
}
