package com.weather.service.ip_api.domain.repository

import com.weather.service.api_client.network_response.NetworkResponse
import com.weather.service.ip_api.data.api.IpApi
import com.weather.service.ip_api.data.dto.IpDto
import com.weather.service.ip_api.domain.mapper.IpDtoMapper
import com.weather.service.ip_api.domain.model.IpDomainResult
import javax.inject.Inject

class IpRepository @Inject constructor(
    private val api: IpApi,
    private val mapper: IpDtoMapper,
) {

    suspend fun fetchLocation(): IpDomainResult =
        when (val response: NetworkResponse<IpDto, Any> = api.fetchLocation()) {
            is NetworkResponse.Success ->
                mapper.mapToDomain(response.body)
            is NetworkResponse.ServerError ->
                IpDomainResult.Failure("Server error code - ${response.code}, message - ${response.error?.message}")
            is NetworkResponse.NetworkError ->
                IpDomainResult.Failure("Network error body - ${response.body}, message - ${response.error.message}")
            is NetworkResponse.UnknownError ->
                IpDomainResult.Failure("Unknown error code - ${response.code}")
        }
}
