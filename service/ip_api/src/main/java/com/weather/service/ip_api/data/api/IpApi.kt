package com.weather.service.ip_api.data.api

import com.weather.service.api_client.network_response.NetworkResponse
import com.weather.service.ip_api.data.dto.IpDto
import retrofit2.http.GET

interface IpApi {

    @GET("json/")
    suspend fun fetchLocation(): NetworkResponse<IpDto, Any>
}
