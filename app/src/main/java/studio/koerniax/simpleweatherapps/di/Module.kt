package studio.koerniax.simpleweatherapps.di

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import studio.koerniax.simpleweatherapps.data.room.AppDataBase

/**
 * Created by KOERNIAX at 16/03/22
 */
object Module {

    private val appModules = module {
        single { Dispatchers.IO }
    }

    private val databaseModule = module {
        single {
            Room.databaseBuilder(androidApplication(), AppDataBase::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    fun getAllModule() = listOf(
        appModules,
        databaseModule,
        DaoModule.modules,
        RepositoryModule.modules,
        NetworkModule.modules,
        ViewModelModule.modules
    )

}