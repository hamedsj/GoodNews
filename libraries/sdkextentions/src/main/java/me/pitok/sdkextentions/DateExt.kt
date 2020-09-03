package me.pitok.sdkextentions

import java.util.*

fun Date.toLeftDate(): String{
    val currentCalendar = Calendar.getInstance()
    val currentDate = Date(currentCalendar.timeInMillis)
    val diffInMilliSecond = currentDate.time - time
    val diffInDays = diffInMilliSecond / (1000*60*60*24)
    return when {
        diffInDays == 0L -> {
            "امروز"
        }
        diffInDays == 1L -> {
            "دیروز"
        }
        diffInDays < 7L -> {
            "$diffInDays روز پیش".en2pr()
        }
        else -> {
            val diffInWeeks = diffInDays/7
            "$diffInWeeks هفته پیش".en2pr()
        }
    }
}