package com.titas.weatherappkuliza.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
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

    fun getWeatherData(key: String, city: String, noOfDays: Int): LiveData<WeatherResponse> {
        val weatherResponseData: MutableLiveData<WeatherResponse> = MutableLiveData<WeatherResponse>()

        val call: Call<WeatherResponse> = weatherService.getWeather(key, city, noOfDays)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                weatherResponseData.value = null
            }

            override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                weatherResponseData.postValue(response?.body())
            }
        })

        return weatherResponseData
    }

}