package studio.koerniax.simpleweatherapps.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import studio.koerniax.simpleweatherapps.data.network.ApiService
import studio.koerniax.simpleweatherapps.data.network.ResultWrapper
import studio.koerniax.simpleweatherapps.data.network.safeApiCall
import studio.koerniax.simpleweatherapps.model.response.LocationResponse
import studio.koerniax.simpleweatherapps.model.response.WeatherResponse

/**
 * Created by KOERNIAX at 16/03/22
 */
class WeatherRepositoryImpl(
    private val api: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getLocationDataAsync(
        query: String,
        limit: Int
    ): ResultWrapper<List<LocationResponse>> {
        return safeApiCall(dispatcher) { api.getLocationsAsync(query, limit) }
    }

    override suspend fun getWeatherDataAsync(
        lat: Double,
        lon: Double,
        exclude: String?,
        unit: String
    ): ResultWrapper<WeatherResponse> {
        return safeApiCall(dispatcher) { api.getWeatherDataAsync(lat, lon, exclude, unit) }
    }

}