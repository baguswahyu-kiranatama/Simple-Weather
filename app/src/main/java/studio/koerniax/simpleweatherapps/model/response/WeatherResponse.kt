package studio.koerniax.simpleweatherapps.model.response

import com.squareup.moshi.Json

/**
 * Created by KOERNIAX at 16/03/22
 */

data class WeatherResponse(
    @field:Json(name = "lat") val lat: Double? = null,
    @field:Json(name = "lon") val lon: Double? = null,
    @field:Json(name = "timezone") val timezone: String? = null,
    @field:Json(name = "timezone_offset") val timezoneOffset: Int? = null,
    @field:Json(name = "current") val current: CurrentWeather? = null,
    @field:Json(name = "hourly") val hourly: List<HourlyForecast>? = null,
    @field:Json(name = "daily") val daily: List<DailyForecast>? = null
)

data class CurrentWeather(
    @field:Json(name = "dt") val timeStamp: Long? = null,
    @field:Json(name = "temp") val temp: Float? = null,
    @field:Json(name = "feels_like") val feelsLike: Float? = null,
    @field:Json(name = "weather") val weather: List<Weather>? = null
)

data class HourlyForecast(
    @field:Json(name = "dt") val timeStamp: Long? = null,
    @field:Json(name = "temp") val temp: Float? = null,
    @field:Json(name = "weather") val weather: List<Weather>? = null
)

data class DailyForecast(
    @field:Json(name = "dt") val timeStamp: Long? = null,
    @field:Json(name = "temp") val temperature: Temperature? = null,
    @field:Json(name = "weather") val weather: List<Weather>? = null
)

data class Temperature(
    @field:Json(name = "min") val minTemp: Float? = null,
    @field:Json(name = "max") val maxTemp: Float? = null
)

data class Weather(
    @field:Json(name = "id") val id: Int? = null,
    @field:Json(name = "main") val main: String? = null,
    @field:Json(name = "description") val description: String? = null,
    @field:Json(name = "icon") val icon: String? = null
)
