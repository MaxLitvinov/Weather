package com.weather.service.ip_api.di

import com.weather.service.ip_api.BuildConfig
import com.weather.service.ip_api.data.api.IpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class IpApiModule {

    @Provides
    fun provideIpApi(retrofitBuilder: Retrofit.Builder): IpApi =
        retrofitBuilder
            .baseUrl(BuildConfig.BASE_URL_IP_API)
            .build()
            .create(IpApi::class.java)
}
