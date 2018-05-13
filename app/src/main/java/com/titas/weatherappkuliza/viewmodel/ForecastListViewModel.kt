package com.titas.weatherappkuliza.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.titas.weatherappkuliza.common.Constants
import com.titas.weatherappkuliza.model.ForecastWrapper
import com.titas.weatherappkuliza.model.repository.WeatherRepository
import javax.inject.Inject

/**
 * Created by Titas on 5/12/2018.
 */
class ForecastListViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {

    private var forecastData: LiveData<ForecastWrapper>

    init {
        forecastData = repository.getWeatherObservable()
    }

    fun getWeather(queryString: String) = repository.getWeather(Constants.API_KEY, queryString, 7)

    fun getWeatherObservable(): LiveData<ForecastWrapper>{
        return forecastData
    }
}