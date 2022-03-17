package studio.koerniax.simpleweatherapps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import studio.koerniax.simpleweatherapps.data.network.ResultWrapper
import studio.koerniax.simpleweatherapps.data.repository.FavoriteLocationRepository
import studio.koerniax.simpleweatherapps.data.repository.WeatherRepository
import studio.koerniax.simpleweatherapps.model.entity.FavoriteLocation
import studio.koerniax.simpleweatherapps.model.response.LocationResponse
import studio.koerniax.simpleweatherapps.model.response.WeatherResponse
import studio.koerniax.simpleweatherapps.utils.Constants

/**
 * Created by KOERNIAX at 16/03/22
 */
class HomeFragmentViewModel(
    private val weatherRepository: WeatherRepository,
    private val favoriteRepository: FavoriteLocationRepository
) : ViewModel() {

    private val _weatherLiveData = MutableLiveData<ResultWrapper<WeatherResponse>>()
    val weatherLiveData: LiveData<ResultWrapper<WeatherResponse>> = _weatherLiveData

    private val _addFavoriteLiveData = MutableSharedFlow<Boolean>()
    val addFavoriteLiveData: Flow<Boolean> = _addFavoriteLiveData

    var currentLocationData: LocationResponse? = null

    fun fetchWeatherData(isRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val exclude = listOf(Constants.EXCLUDE_MINUTELY, Constants.EXCLUDE_ALERTS)
                .joinToString(",")
            if (isRefresh.not()) _weatherLiveData.postValue(ResultWrapper.Loading)
            if (currentLocationData == null) {
                currentLocationData = LocationResponse().createDefaultData()
            }
            _weatherLiveData.postValue(
                weatherRepository.getWeatherDataAsync(
                    lat = currentLocationData!!.lat,
                    lon = currentLocationData!!.lon,
                    exclude = exclude
                )
            )

        }
    }

    fun addToFavorite() {
        currentLocationData?.let {
            val data = FavoriteLocation(
                locationName = it.name ?: "",
                locationState = it.state ?: "",
                locationCountry = it.country ?: "",
                lat = it.lat,
                lng = it.lon
            )
            viewModelScope.launch(Dispatchers.IO) {
                val result = favoriteRepository.insertData(data)
                _addFavoriteLiveData.emit(result > 0)
            }
        }
    }

}