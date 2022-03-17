package studio.koerniax.simpleweatherapps.model.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by KOERNIAX at 16/03/22
 */

@Entity(
    tableName = FavoriteLocation.TABLE_NAME,
    indices = [Index(value = ["lat", "lng"], unique = true)]
)
@Parcelize
data class FavoriteLocation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "location_name")
    val locationName: String,

    @ColumnInfo(name = "location_state")
    val locationState: String? = null,

    @ColumnInfo(name = "location_country")
    val locationCountry: String? = null,

    @ColumnInfo(name = "lat")
    val lat: Double,

    @ColumnInfo(name = "lng")
    val lng: Double
) : Parcelable {
    companion object {
        const val TABLE_NAME = "favorite_location"
    }
}