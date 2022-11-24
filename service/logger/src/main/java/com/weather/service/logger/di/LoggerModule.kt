package com.weather.service.logger.di

import com.weather.service.logger.BuildConfig
import com.weather.service.logger.DebugLogger
import com.weather.service.logger.Logger
import com.weather.service.logger.ReleaseLogger
import com.weather.library.time.TimestampProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoggerModule {

    @Provides
    fun provideLogger(
        timestampProvider: TimestampProvider
    ): Logger = when {
        BuildConfig.DEBUG -> {
            //  TODO: BenchmarkLogger(timestampProvider)
            DebugLogger()
        }
        else -> ReleaseLogger()
    }
}
