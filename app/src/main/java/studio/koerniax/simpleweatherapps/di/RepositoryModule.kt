package studio.koerniax.simpleweatherapps.di

import org.koin.dsl.module
import studio.koerniax.simpleweatherapps.data.repository.FavoriteLocationRepositoryImpl
import studio.koerniax.simpleweatherapps.data.repository.FavoriteLocationRepository
import studio.koerniax.simpleweatherapps.data.repository.WeatherRepository
import studio.koerniax.simpleweatherapps.data.repository.WeatherRepositoryImpl

/**
 * Created by KOERNIAX at 16/03/22
 */
object RepositoryModule {

    val modules = module {
        single<FavoriteLocationRepository> { FavoriteLocationRepositoryImpl(get()) }
        single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
    }

}