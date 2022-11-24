package com.weather.service.logger

import com.weather.library.time.TimestampProvider
import com.weather.service.logger.tree.FileReportingTree
import timber.log.Timber

class BenchmarkLogger(
    private val timestampProvider: TimestampProvider,
) : Logger {

    override fun initTrees() {
        Timber.plant(FileReportingTree(timestampProvider))
    }

    override fun report(message: String) {
        Timber.i(message)
    }

    override fun report(throwable: Throwable) {
        Timber.i(throwable)
    }
}
