package com.titas.weatherappkuliza.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.titas.weatherappkuliza.common.Constants
import com.titas.weatherappkuliza.model.WeatherResponse
import com.titas.weatherappkuliza.repository.WeatherRepository

/**
 * Created by Titas on 5/12/2018.
 */
class ForecastListViewModel(application: Application): AndroidViewModel(application) {

    fun getWeather(city: String) = WeatherRepository.getWeatherData(Constants.API_KEY, city, 7)


}