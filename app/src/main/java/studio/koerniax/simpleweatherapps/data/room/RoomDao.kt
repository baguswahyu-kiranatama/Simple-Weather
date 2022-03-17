package studio.koerniax.simpleweatherapps.data.room

import studio.koerniax.simpleweatherapps.data.dao.FavoriteLocationDao

/**
 * Created by KOERNIAX at 16/03/22
 */
class RoomDao(private val dataBase: AppDataBase) {

    fun favoriteLocationDao(): FavoriteLocationDao = dataBase.favoriteLocationDao

}