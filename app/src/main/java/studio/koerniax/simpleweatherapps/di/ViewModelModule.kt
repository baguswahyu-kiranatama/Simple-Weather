package studio.koerniax.simpleweatherapps.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import studio.koerniax.simpleweatherapps.viewmodel.FavoriteFragmentViewModel
import studio.koerniax.simpleweatherapps.viewmodel.HomeFragmentViewModel
import studio.koerniax.simpleweatherapps.viewmodel.SearchFragmentViewModel

/**
 * Created by KOERNIAX at 16/03/22
 */
object ViewModelModule {

    val modules = module {
        viewModel { HomeFragmentViewModel(get(), get()) }
        viewModel { SearchFragmentViewModel(get()) }
        viewModel { FavoriteFragmentViewModel(get()) }
    }

}