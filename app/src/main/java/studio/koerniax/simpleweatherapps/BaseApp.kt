package studio.koerniax.simpleweatherapps

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import studio.koerniax.simpleweatherapps.di.Module

/**
 * Created by KOERNIAX at 16/03/22
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = this
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@BaseApp)
            modules(Module.getAllModule())
        }
    }

    companion object {
        lateinit var appContext: BaseApp
            private set
    }

}