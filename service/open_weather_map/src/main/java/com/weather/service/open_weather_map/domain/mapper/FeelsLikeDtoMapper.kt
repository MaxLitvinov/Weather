package com.weather.service.open_weather_map.domain.mapper

import com.weather.service.open_weather_map.data.dto.FeelsLikeDto
import com.weather.service.open_weather_map.domain.model.FeelsLikeDomainModel
import javax.inject.Inject

class FeelsLikeDtoMapper @Inject constructor() {

    fun mapToDomainModel(dto: FeelsLikeDto) = with(dto) {
        FeelsLikeDomainModel(
            day = day,
            night = night,
            evening = evening,
            morning = morning
        )
    }
}
