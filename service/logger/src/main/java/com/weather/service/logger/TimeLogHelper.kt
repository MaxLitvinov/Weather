package com.weather.service.logger

import com.weather.library.time.TimestampProvider
import java.util.Date
import javax.inject.Inject

class TimeLogHelper @Inject constructor(
    private val timestampProvider: TimestampProvider,
) {

    companion object {

        private const val EMPTY = -1L
    }

    private var startTimestamp: Long = EMPTY
    private var endTimestamp: Long = EMPTY

    fun produceLog(message: String): String {
        var resultMessage = message

        when {
            EMPTY == startTimestamp -> startTimestamp = Date().time
            EMPTY == endTimestamp -> {
                endTimestamp = Date().time

                val startFullTimestampData = timestampProvider.toFileReportPattern(startTimestamp)
                val endFullTimestampData = timestampProvider.toFileReportPattern(endTimestamp)
                val difference = endTimestamp - startTimestamp

                resultMessage = StringBuilder("\n")
                    .append("START $startFullTimestampData [$startTimestamp]\n")
                    .append("  END $endFullTimestampData [$endTimestamp]\n")
                    .append("                   difference  $difference\n")
                    .append(message)
                    .toString()

                startTimestamp = EMPTY
                endTimestamp = EMPTY
            }
            else -> resultMessage = message
        }

        return resultMessage
    }
}
