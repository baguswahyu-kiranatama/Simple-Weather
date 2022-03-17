package studio.koerniax.simpleweatherapps.di

import org.koin.dsl.module
import studio.koerniax.simpleweatherapps.data.room.RoomDao

/**
 * Created by KOERNIAX at 16/03/22
 */
object DaoModule {

    val modules = module {
        single { RoomDao(get()).favoriteLocationDao() }
    }

}