package studio.koerniax.simpleweatherapps.utils

import studio.koerniax.simpleweatherapps.BaseApp
import studio.koerniax.simpleweatherapps.R
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

/**
 * Created by KOERNIAX at 16/03/22
 */
object Helper {

    fun millisToHour(millis: Long?): String? {
        millis?.let { timeStamp ->
            val time = Instant.ofEpochSecond(timeStamp).atZone(ZoneId.systemDefault()).toLocalTime()
            return time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
        } ?: return null
    }

    fun millisToDate(millis: Long?): String? {
        millis?.let { timeStamp ->
            val dateTime =
                Instant.ofEpochSecond(timeStamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
            val currentDateTime = LocalDateTime.now()
            return when (dateTime.dayOfYear - currentDateTime.dayOfYear) {
                0 -> BaseApp.appContext.getString(R.string.text_today)
                1 -> BaseApp.appContext.getString(R.string.text_tomorrow)
                else -> dateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
            }
        } ?: return null
    }

    fun millisToDateTime(millis: Long?): String? {
        millis?.let { timeStamp ->
            val dateTime =
                Instant.ofEpochSecond(timeStamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
            return dateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(
                    FormatStyle.FULL,
                    FormatStyle.SHORT
                )
            )
        } ?: return null
    }

    fun combineStateAndCountry(state: String?, country: String?): String {
        country?.let {
            val countryName = Locale("", it).displayCountry
            state?.let { stateName ->
                return "$stateName, $countryName"
            } ?: return countryName
        } ?: return ""
    }

}