package com.weather.service.ip_api.domain.repository

import com.weather.service.api_client.network_response.NetworkResponse
import com.weather.service.ip_api.data.api.IpApi
import com.weather.service.ip_api.data.dto.IpDto
import com.weather.service.ip_api.domain.mapper.IpDtoMapper
import com.weather.service.ip_api.domain.model.IpDomainResult
import com.weather.service.logger.Logger
import javax.inject.Inject

class IpRepository @Inject constructor(
    private val api: IpApi,
    private val mapper: IpDtoMapper,
    private val logger: Logger
) {

    suspend fun fetchLocation(): IpDomainResult =
        when (val response: NetworkResponse<IpDto, Any> = api.fetchLocation()) {
            is NetworkResponse.Success ->
                mapper.mapToDomain(response.body)
            is NetworkResponse.ServerError ->
                handleError("Server error code - ${response.code}, message - ${response.error?.message}")
            is NetworkResponse.NetworkError ->
                handleError("Network error body - ${response.body}, message - ${response.error.message}")
            is NetworkResponse.UnknownError ->
                handleError("Unknown error code - ${response.code}")
        }

    private fun handleError(errorMessage: String): IpDomainResult.Failure {
        logger.report(errorMessage)
        return IpDomainResult.Failure(errorMessage)
    }
}
