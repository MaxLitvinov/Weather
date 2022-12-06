package com.weather.service.logger

import timber.log.Timber

class DebugLogger(
    private val timeLogHelper: TimeLogHelper
) : Logger {

    override fun initTrees() {
        Timber.plant(Timber.DebugTree())
    }

    override fun report(message: String) {
        val transformedMessage = timeLogHelper.produceLog(message)
        Timber.tag(this::class.java.simpleName)
        Timber.d(transformedMessage)
    }

    override fun report(throwable: Throwable) {
        Timber.e(throwable)
    }
}
