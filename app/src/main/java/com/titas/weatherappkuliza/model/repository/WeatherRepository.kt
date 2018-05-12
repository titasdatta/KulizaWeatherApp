package com.titas.weatherappkuliza.model.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.titas.weatherappkuliza.model.ForecastWrapper
import com.titas.weatherappkuliza.model.WeatherResponse
import com.titas.weatherappkuliza.model.repository.network.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Titas on 5/10/2018.
 */
@Singleton
open class WeatherRepository @Inject constructor(val weatherService: WeatherService) {
    val weatherResponseData: MutableLiveData<ForecastWrapper> = MutableLiveData<ForecastWrapper>()

    fun getWeatherData(key: String, city: String, noOfDays: Int): LiveData<ForecastWrapper> {
        val call: Call<WeatherResponse> = weatherService.getWeather(key, city, noOfDays)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                val forecastWrapper = ForecastWrapper(null)
                weatherResponseData.postValue(forecastWrapper)
            }

            override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                val forecastWrapper = ForecastWrapper(response?.body())
                weatherResponseData.postValue(forecastWrapper)

            }
        })

        return weatherResponseData
    }

}