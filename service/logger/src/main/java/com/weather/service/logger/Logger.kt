package com.weather.service.logger

interface Logger {

    /**
     * Init tree(s) once in the [com.weather.service.logger.di.LoggerModule]
     */
    fun initTrees()

    fun report(message: String)

    fun report(throwable: Throwable)
}
