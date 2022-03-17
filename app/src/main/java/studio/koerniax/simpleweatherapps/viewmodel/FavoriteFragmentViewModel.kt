package studio.koerniax.simpleweatherapps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import studio.koerniax.simpleweatherapps.data.repository.FavoriteLocationRepository
import studio.koerniax.simpleweatherapps.model.entity.FavoriteLocation

/**
 * Created by KOERNIAX at 17/03/22
 */
class FavoriteFragmentViewModel(
    private val favoriteRepository: FavoriteLocationRepository
) : ViewModel() {

    private val _favoriteLiveData = MutableLiveData<List<FavoriteLocation>>()
    val favoriteLiveData: LiveData<List<FavoriteLocation>> = _favoriteLiveData

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.fetchAllDataAsync().collect {
                _favoriteLiveData.postValue(it)
            }
        }
    }

    fun deleteData(id: Long) {
        viewModelScope.launch {
            favoriteRepository.deleteDataById(id)
        }
    }

}