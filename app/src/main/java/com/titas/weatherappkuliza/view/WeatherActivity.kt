package com.titas.weatherappkuliza.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.titas.weatherappkuliza.R
import com.titas.weatherappkuliza.common.Constants
import com.titas.weatherappkuliza.model.WeatherResponse
import com.titas.weatherappkuliza.repository.WeatherRepository
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    lateinit var weatherResponseLiveData : LiveData<WeatherResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        test_button.setOnClickListener {
            weatherResponseLiveData = WeatherRepository.getWeatherData(Constants.API_KEY, "bengaluru", 7)
            weatherResponseLiveData.observe(this, object : Observer<WeatherResponse> {
                override fun onChanged(t: WeatherResponse?) {
                    Log.d("Weather", "Weather Response : ${t.toString()}")
                }
            })
        }
    }
}
