package com.weather.service.logger

import com.weather.service.logger.tree.CrashReportingTree
import timber.log.Timber

class ReleaseLogger : Logger {

    override fun initTrees() {
        Timber.plant(CrashReportingTree())
    }

    override fun report(message: String) {
        // TODO: FirebaseCrashlytics.getInstance().log(message)
    }

    override fun report(throwable: Throwable) {
        // TODO: FirebaseCrashlytics.getInstance().recordException(throwable)
    }
}
