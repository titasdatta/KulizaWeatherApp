package com.titas.weatherappkuliza.repository.network

import com.titas.weatherappkuliza.common.Constants
import com.titas.weatherappkuliza.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.Executors

/**
 * Created by Titas on 5/8/2018.
 */
interface WeatherService {
    @GET("forecast.json")
    fun getWeather(@Query("key") key: String, @Query("q") city: String, @Query("days") dayCount: Int) : Call<WeatherResponse>

    companion object {
        val instance: WeatherService by lazy {
            val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    //Performs the API Call on a worker thread
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build()
            retrofit.create(WeatherService::class.java)
        }
    }
}