package com.titas.weatherappkuliza.viewmodel

import android.arch.lifecycle.ViewModel
import com.titas.weatherappkuliza.common.Constants
import com.titas.weatherappkuliza.model.repository.WeatherRepository
import javax.inject.Inject

/**
 * Created by Titas on 5/12/2018.
 */
class ForecastListViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {

    fun getWeather(city: String) = repository.getWeatherData(Constants.API_KEY, city, 7)


}