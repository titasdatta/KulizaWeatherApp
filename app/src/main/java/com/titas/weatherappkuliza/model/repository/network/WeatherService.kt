package com.titas.weatherappkuliza.model.repository.network

import com.titas.weatherappkuliza.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Titas on 5/8/2018.
 */
interface WeatherService {
    @GET("forecast.json")
    fun getWeather(@Query("key") key: String, @Query("q") city: String, @Query("days") dayCount: Int) : Call<WeatherResponse>
}