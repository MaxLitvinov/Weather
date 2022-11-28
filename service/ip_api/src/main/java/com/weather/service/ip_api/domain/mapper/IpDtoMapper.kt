package com.weather.service.ip_api.domain.mapper

import com.weather.service.ip_api.data.dto.IpDto
import com.weather.service.ip_api.domain.model.IpDomainResult
import javax.inject.Inject

class IpDtoMapper @Inject constructor() {

    fun mapToDomain(dto: IpDto) = with(dto) {
        IpDomainResult.Success(
            query = query,
            status = status,
            country = country,
            countryCode = countryCode,
            region = region,
            regionName = regionName,
            city = city,
            zip = zip,
            lat = lat,
            lon = lon,
            timezone = timezone,
            isp = isp,
            org = org,
            _as = _as
        )
    }
}
