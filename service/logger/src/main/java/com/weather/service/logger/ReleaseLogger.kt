package com.weather.service.logger

import com.weather.service.logger.tree.CrashReportingTree
import timber.log.Timber

class ReleaseLogger : Logger {

    override fun initTrees() {
        Timber.plant(CrashReportingTree())
    }

    override fun report(message: String) {
        Timber.tag(ReleaseLogger::class.java.simpleName)
        Timber.e(message)
    }

    override fun report(throwable: Throwable) {
        Timber.e(throwable)
    }
}
