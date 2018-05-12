package com.titas.weatherappkuliza.dagger.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.titas.weatherappkuliza.dagger.ViewModelKey
import com.titas.weatherappkuliza.viewmodel.ForecastListViewModel
import com.titas.weatherappkuliza.viewmodel.ForecastViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Titas on 5/13/2018.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ForecastListViewModel::class)
    internal abstract fun bindForecastListViewModel(forecastListViewModel: ForecastListViewModel): ViewModel

    @Binds
    internal abstract fun bindForecastViewModelFactory(factory: ForecastViewModelFactory) : ViewModelProvider.Factory
}