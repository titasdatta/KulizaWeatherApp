package com.titas.weatherappkuliza.view

import android.animation.Animator
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.TranslateAnimation
import com.titas.weatherappkuliza.R
import com.titas.weatherappkuliza.adapter.ForecastListAdapter
import com.titas.weatherappkuliza.common.Constants
import com.titas.weatherappkuliza.model.ForecastWrapper
import com.titas.weatherappkuliza.model.StatusCode
import com.titas.weatherappkuliza.model.WeatherResponse
import com.titas.weatherappkuliza.repository.WeatherRepository
import com.titas.weatherappkuliza.viewmodel.ForecastListViewModel
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    lateinit var weatherResponseLiveData : LiveData<ForecastWrapper>
    lateinit var forecastViewModel: ForecastListViewModel
    lateinit var forecastListAdapter: ForecastListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        title = "Kuliza Weather App"

        forecastViewModel = ViewModelProviders.of(this).get(ForecastListViewModel::class.java)

        weatherResponseLiveData = forecastViewModel.getWeather("Bengaluru")

        val layoutManager = LinearLayoutManager(this)
        forecast_list.layoutManager = layoutManager
        forecast_list.setHasFixedSize(true)

        weatherResponseLiveData.observe(this, object : Observer<ForecastWrapper>{
            override fun onChanged(t: ForecastWrapper?) {
                val weatherResponse = t
                hideProgress()
                if(weatherResponse != null && weatherResponse.status == StatusCode.OK){
                    //success path
                    initForecastScreen(weatherResponse)
                }else{
                    //failure path
                    showFailureScreen()
                }
            }
        })
    }

    /**
     * Hides the progress loader
     */
    private fun hideProgress(){
        //animate this later
        progress_container.animate().alpha(0.0f).setDuration(200).setListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                progress_container.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
    }

    private fun showProgress(){
        progress_container.visibility = View.VISIBLE
    }

    private fun initForecastScreen(forecastData: ForecastWrapper?){
        forecastData?.let {
            val currentTemp = "${forecastData.currentTemp}" + "\u00B0"
            current_temp.text = "${currentTemp}"
            current_location.text = "${forecastData.city}"
            forecastListAdapter = ForecastListAdapter(forecastData.forecastList)
            forecast_list.adapter = forecastListAdapter
            success_path_container.visibility = View.VISIBLE
            //animate list from below
            slideUp()
        }
    }

    private fun slideUp(){
        val handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
                forecast_list.visibility = View.VISIBLE
                val translateAnimation = TranslateAnimation(0f, 0f, forecast_list.height.toFloat(), 0f)
                translateAnimation.duration = 600
                translateAnimation.interpolator = AccelerateDecelerateInterpolator()
                translateAnimation.fillAfter = true
                forecast_list.startAnimation(translateAnimation)
            }
        }, 500)
    }

    private fun showFailureScreen(){
        failure_path_container.visibility = View.VISIBLE
        success_path_container.visibility = View.GONE
        retry_btn.setOnClickListener {
            failure_path_container.visibility = View.GONE
            showProgress()
            forecastViewModel.getWeather("bengaluru")
        }
    }
}
