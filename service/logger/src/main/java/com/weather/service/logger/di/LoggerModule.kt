package com.weather.service.logger.di

import com.weather.service.logger.BuildConfig
import com.weather.service.logger.DebugLogger
import com.weather.service.logger.Logger
import com.weather.service.logger.ReleaseLogger
import com.weather.service.logger.TimeLogHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoggerModule {

    @Provides
    @Singleton
    fun provideLogger(
        timeLogHelper: TimeLogHelper
    ): Logger = when {
        BuildConfig.DEBUG -> DebugLogger(timeLogHelper)
        else -> ReleaseLogger()
    }
}
