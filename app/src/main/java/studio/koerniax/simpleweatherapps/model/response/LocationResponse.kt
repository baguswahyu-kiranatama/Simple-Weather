package studio.koerniax.simpleweatherapps.model.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import studio.koerniax.simpleweatherapps.utils.Constants

/**
 * Created by KOERNIAX at 16/03/22
 */

@Parcelize
data class LocationResponse(
    @field:Json(name = "name") val name: String? = null,
    @field:Json(name = "lat") val lat: Double = 0.0,
    @field:Json(name = "lon") val lon: Double = 0.0,
    @field:Json(name = "country") val country: String? = null,
    @field:Json(name = "state") val state: String? = null
) : Parcelable {
    fun createDefaultData(): LocationResponse {
        return LocationResponse(
            name = Constants.DEFAULT_CITY,
            lat = Constants.DEFAULT_LAT,
            lon = Constants.DEFAULT_LON,
            country = Constants.DEFAULT_COUNTRY
        )
    }
}