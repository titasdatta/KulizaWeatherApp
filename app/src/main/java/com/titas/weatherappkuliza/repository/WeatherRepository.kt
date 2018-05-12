package com.titas.weatherappkuliza.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.titas.weatherappkuliza.model.ForecastWrapper
import com.titas.weatherappkuliza.model.WeatherResponse
import com.titas.weatherappkuliza.repository.network.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Titas on 5/10/2018.
 */
object WeatherRepository {
    private val weatherService: WeatherService = WeatherService.instance
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