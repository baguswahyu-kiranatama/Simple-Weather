package studio.koerniax.simpleweatherapps.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import studio.koerniax.simpleweatherapps.data.dao.FavoriteLocationDao
import studio.koerniax.simpleweatherapps.model.entity.FavoriteLocation

/**
 * Created by KOERNIAX at 16/03/22
 */

@Database(
    entities = [
        FavoriteLocation::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {

    abstract val favoriteLocationDao: FavoriteLocationDao

}