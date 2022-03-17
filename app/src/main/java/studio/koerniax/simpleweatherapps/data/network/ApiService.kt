package studio.koerniax.simpleweatherapps.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import studio.koerniax.simpleweatherapps.model.response.LocationResponse
import studio.koerniax.simpleweatherapps.model.response.WeatherResponse
import studio.koerniax.simpleweatherapps.utils.Constants

/**
 * Created by KOERNIAX at 16/03/22
 */
interface ApiService {

    @GET("/geo/1.0/direct")
    suspend fun getLocationsAsync(
        @Query("q") location: String,
        @Query("limit") limit: Int
    ): List<LocationResponse>

    @GET("/data/2.5/onecall")
    suspend fun getWeatherDataAsync(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String? = null,
        @Query("units") unit: String = Constants.UNIT_METRIC
    ): WeatherResponse

}