package com.weather.feature.home_page

import android.content.Context
import com.weather.feature.home_page.mapper.DailyDomainModelListMapper
import com.weather.feature.home_page.model.DayForecast
import com.weather.foundation.resources.R
import com.weather.library.time.TimestampProvider
import com.weather.service.open_weather_map.domain.model.DailyDomainModel
import com.weather.service.open_weather_map.domain.model.FeelsLikeDomainModel
import com.weather.service.open_weather_map.domain.model.TemperatureDomainModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DailyDomainModelListMapperTest {

    private lateinit var context: Context
    private lateinit var timestampProvider: TimestampProvider
    private lateinit var DailyDomainModelListMapper: DailyDomainModelListMapper

    @Before
    fun setup() {
        context = mockk()
        timestampProvider = mockk()
        DailyDomainModelListMapper = DailyDomainModelListMapper(context, timestampProvider)
    }

    @Test
    fun `Check domain model mapping into UI model`() {
        val dailyTime = 1663146000L
        val dailyDomainModel = DailyDomainModel(
            time = dailyTime,
            sunriseTime = 1663126067,
            sunsetTime = 1663172008,
            moonriseTime = 1663177560,
            moonsetTime = 1663143240,
            moonPhase = 0.64F,
            temperature = TemperatureDomainModel(
                day = 15.07F,
                min = 9.34F,
                max = 17.26F,
                night = 11.26F,
                evening = 12.31F,
                morning = 9.34F
            ),
            feelsLike = FeelsLikeDomainModel(
                day = 14.43F,
                night = 10.9F,
                evening = 11.97F,
                morning = 8.52F
            ),
            pressure = 1005,
            humidity = 69,
            dewPoint = 9.46F,
            windSpeed = 6.0F,
            windDeg = 254,
            windGust = 8.66F,
            clouds = 93,
            pop = 0.57F,
            rain = 2.39F,
            uvIndex = 2.34F,
            snow = null,
            hourlyForecasts = listOf(mockk())
        )

        val dayName = "14.09 Wednesday"
        every { timestampProvider.toDayMonthAndDayName(dailyDomainModel.time) } returns dayName

        every { context.getString(R.string.home_page_today) } returns "Today"
        every { context.getString(R.string.home_page_tomorrow) } returns "Tomorrow"

        val dayTemperature = "15.07°C"
        val nightTemperature = "11.26°C"
        every { context.getString(R.string.home_page_day_night_temperature_values, dayTemperature, nightTemperature) } returns "$dayTemperature / $nightTemperature"
        every { context.getString(R.string.home_page_temperature_value, dailyDomainModel.temperature.day) } returns dayTemperature
        every { context.getString(R.string.home_page_temperature_value, dailyDomainModel.temperature.night) } returns nightTemperature

        val currentDayTime = 1662936000L
        every { timestampProvider.toDayMonthAndDayName(currentDayTime) } returns "Today"
        every { timestampProvider.toDayMonthAndDayName(1663022400L) } returns "12.09 Monday"

        val dailyDomainModelList: List<DailyDomainModel> = listOf(dailyDomainModel)

        val actualUiModelList: List<DayForecast> = DailyDomainModelListMapper.mapToUiModel(currentDayTime, dailyDomainModelList)

        val expectedUiModelList: List<DayForecast> = listOf(
            DayForecast(
                id = dailyTime,
                dayName = dayName,
                dayNightTemperature = "15.07°C / 11.26°C"
            )
        )

        assertEquals(expectedUiModelList, actualUiModelList)
    }
}
