package com.weather.feature.home_page.mapper

import android.content.Context
import com.weather.feature.home_page.model.DayForecast
import com.weather.foundation.resources.R
import com.weather.library.time.TimestampProvider
import com.weather.service.open_weather_map.domain.model.DailyDomainModel
import com.weather.service.open_weather_map.domain.model.TemperatureDomainModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DailyDomainModelListMapper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val timestampProvider: TimestampProvider
) {

    companion object {

        const val ONE_DAY_IN_SECONDS = 86_400
    }

    fun mapToUiModel(
        currentDayTime: Long,
        domainList: List<DailyDomainModel>
    ): List<DayForecast> =
        getDayNightTemperatureMaxLengths(domainList).let { (dayTempMaxLength, nightTempMaxLength) ->
            domainList.map { dailyDomainModel ->
                dailyDomainModel.createDayForecast(
                    currentDayTime,
                    dayTempMaxLength,
                    nightTempMaxLength
                )
            }
        }

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

    private fun TemperatureDomainModel.getDayNightTemperature(
        dayMaxLength: Int,
        nightMaxLength: Int
    ) = context.getString(
        R.string.home_page_day_night_temperature_values,
        transformDayTemperature(day, dayMaxLength),
        transformNightTemperature(night, nightMaxLength)
    )

    private fun transformDayTemperature(sourceTemperature: Float, maxStringLength: Int): String =
        sourceTemperature.getTemperature(maxStringLength) { temperature, spaces ->
            temperature + spaces
        }

    private fun Float.getTemperature(
        maxLength: Int,
        action: (temperature: String, spaces: String) -> String
    ): String {
        val currentLength = toString().length
        var resultValue: String = context.getString(R.string.home_page_temperature_value, this)
        if (currentLength < maxLength) {
            val lengthDifference = maxLength - currentLength
            resultValue = action(resultValue, " ".repeat(lengthDifference))
        }
        return resultValue
    }

    private fun transformNightTemperature(sourceTemperature: Float, maxStringLength: Int): String =
        sourceTemperature.getTemperature(maxStringLength) { value, spaces ->
            spaces + value
        }

    private fun DailyDomainModel.createDayForecast(
        currentDayTime: Long,
        dayTempMaxLength: Int,
        nightTempMaxLength: Int
    ) = DayForecast(
        id = generateId(),
        dayName = generateDayName(currentDayTime),
        dayNightTemperature = temperature.getDayNightTemperature(
            dayTempMaxLength,
            nightTempMaxLength
        )
    )

    private fun DailyDomainModel.generateId(): Long? =
        hourlyForecasts?.let { time }

    private fun DailyDomainModel.generateDayName(currentDayTime: Long): String {
        val dayName = timestampProvider.toDayMonthAndDayName(time)
        return when {
            isToday(currentDayTime, dayName) -> context.getString(R.string.home_page_today)
            isTomorrow(currentDayTime, dayName) -> context.getString(R.string.home_page_tomorrow)
            else -> dayName
        }
    }

    private fun isToday(currentTime: Long, dayNameToCompare: String): Boolean {
        val currentDayName = timestampProvider.toDayMonthAndDayName(currentTime)
        return currentDayName == dayNameToCompare
    }

    private fun isTomorrow(currentTimeInSeconds: Long, dayNameToCompare: String): Boolean {
        val tomorrowTime = currentTimeInSeconds + ONE_DAY_IN_SECONDS
        val tomorrowDayName = timestampProvider.toDayMonthAndDayName(tomorrowTime)
        return tomorrowDayName == dayNameToCompare
    }
}
