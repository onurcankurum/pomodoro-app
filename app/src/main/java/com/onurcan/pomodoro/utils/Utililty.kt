package com.onurcan.pomodoro.utils

import android.icu.util.TimeUnit

object Utility {

    //time to countdown - 1hr - 60secs
    const val TIME_COUNTDOWNLONG: Long =1500000L
    const val TIME_COUNTDOWNSHORT: Long =300000L
    private const val TIME_FORMAT = "%02d:%02d"


    //convert time to milli seconds
    fun Long.formatTime(): String = String.format(
        TIME_FORMAT,
        java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(this),
        java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )

}