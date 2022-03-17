package studio.koerniax.simpleweatherapps.data.repository

import studio.koerniax.simpleweatherapps.data.network.ResultWrapper
import studio.koerniax.simpleweatherapps.model.response.LocationResponse
import studio.koerniax.simpleweatherapps.model.response.WeatherResponse
import studio.koerniax.simpleweatherapps.utils.Constants

/**
 * Created by KOERNIAX at 16/03/22
 */
interface WeatherRepository {

    suspend fun getWeatherDataAsync(
        lat: Double = Constants.DEFAULT_LAT,
        lon: Double = Constants.DEFAULT_LON,
        exclude: String? = null,
        unit: String = Constants.UNIT_METRIC
    ): ResultWrapper<WeatherResponse>

    suspend fun getLocationDataAsync(
        query: String,
        limit: Int
    ): ResultWrapper<List<LocationResponse>>

}