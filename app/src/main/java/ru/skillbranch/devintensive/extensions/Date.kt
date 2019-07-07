package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = (date.time - this.time)
    val seconds = diff / SECOND
    var output = ""
    output += when (diff / SECOND) {
        in -1L..1L -> "только что"
        in 1L..45L -> "несколько секунд назад"
        in -45L..-1L -> "через несколько секунд"
        in 45L..75L -> "минуту назад"
        in -75L..-45L -> "через минуту"
        in 75L..2700L -> "${diff / MINUTE} ${if (seconds < 120) "минуту" else if (seconds < 300) "минуты" else "минут"} назад"
        in -2700L..-75L -> "через ${-diff / MINUTE} ${if (seconds > -120) "минуту" else if (seconds > -300) "минуты" else "минут"}"
        in 2700L..4500L -> "час назад"
        in -4500L..-2700L -> "через час"
        in 4500L..79200L -> "${diff / HOUR} ${if (seconds < 7200) "час" else if (seconds < 18000) "часа" else "часов"} назад"
        in -79200L..-4500L -> "через ${-diff / HOUR} ${if (seconds > -7200) "час" else if (seconds > -18000) "часа" else "часов"}"
        in 79200L..93600L -> "день назад"
        in -93600L..-79200L -> "через день"
        in 93600L..31104000L -> "${diff / DAY} ${if (seconds < 172800) "день" else if (seconds < 432000) "дня" else "дней"} назад"
        in -31104000L..-93600L -> "через ${-diff / DAY} ${if (seconds > -172800) "день" else if (seconds > -432000) "дня" else "дней"}"
        in 31104000L..Long.MAX_VALUE -> "более года назад"
        else -> "более чем через год"
    }
    return output
}
enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;
    fun plural(value: Int): String {
        if (this == SECOND){
            if (value % 100 in 11..19)
                return "$value секунд"
            else {
                when(value % 10) {
                    1 -> return "1 секунду"
                    in 2..4 -> return "$value секунды"
                    in 5..9, 0 -> return "$value секунд"
                }
            }
        }
        else if (this == MINUTE){
            if (value % 100 in 11..19)
                return "$value минут"
            else {
                when (value % 10) {
                    1 -> return "1 минуту"
                    in 2..4 -> return "$value минуты"
                    in 5..9, 0 -> return "$value минут"
                }
            }
        }
        else if (this == HOUR){
            if (value % 100 in 11..19)
                return "$value часов"
            else {
                when (value % 10) {
                    1 -> return "1 час"
                    in 2..4 -> return "$value часа"
                    in 5..9, 0 -> return "$value часов"
                }
            }
        }
        else {
            if (value % 100 in 11..19)
                return "$value дней"
            else {
                when (value % 10) {
                    1 -> return "1 день"
                    in 2..4 -> return "$value дня"
                    in 5..9, 0 -> return "$value дней"
                }
            }
        }
        return ""
    }
}