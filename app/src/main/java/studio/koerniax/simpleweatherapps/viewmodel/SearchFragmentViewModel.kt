package studio.koerniax.simpleweatherapps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import studio.koerniax.simpleweatherapps.data.network.ResultWrapper
import studio.koerniax.simpleweatherapps.data.repository.WeatherRepository
import studio.koerniax.simpleweatherapps.model.response.LocationResponse

/**
 * Created by KOERNIAX at 17/03/22
 */
class SearchFragmentViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private var locationSearchJob: Job? = null

    private val _locationLiveData = MutableLiveData<ResultWrapper<List<LocationResponse>>>()
    val locationLiveData: LiveData<ResultWrapper<List<LocationResponse>>> = _locationLiveData

    fun fetchLocationData(query: String) {
        locationSearchJob?.cancel()
        locationSearchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(750L)
            _locationLiveData.postValue(ResultWrapper.Loading)
            _locationLiveData.postValue(weatherRepository.getLocationDataAsync(query, 5))
        }
    }

}