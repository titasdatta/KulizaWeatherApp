package com.titas.weatherappkuliza.view

import android.animation.Animator
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.TranslateAnimation
import com.titas.weatherappkuliza.R
import com.titas.weatherappkuliza.adapter.ForecastListAdapter
import com.titas.weatherappkuliza.common.Utils
import com.titas.weatherappkuliza.dagger.WeatherApplication
import com.titas.weatherappkuliza.model.ForecastWrapper
import com.titas.weatherappkuliza.model.StatusCode
import com.titas.weatherappkuliza.viewmodel.ForecastListViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_weather.*
import javax.inject.Inject

class WeatherActivity : AppCompatActivity() {

    lateinit var weatherResponseLiveData : LiveData<ForecastWrapper>
    lateinit var forecastListAdapter: ForecastListAdapter
    lateinit var forecastViewModel: ForecastListViewModel
    @Inject
    lateinit var utils: Utils
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WeatherApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        title = "Kuliza Weather App"

        forecastViewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastListViewModel::class.java)

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
        progress_container.alpha = 1.0f
    }

    private fun initForecastScreen(forecastData: ForecastWrapper?){
        forecastData?.let {
            val currentTemp = "${forecastData.currentTemp}" + "\u00B0"
            current_temp.text = "${currentTemp}"
            current_location.text = "${forecastData.city}"
            forecastListAdapter = ForecastListAdapter(forecastData.forecastList, utils)
            forecast_list.adapter = forecastListAdapter
            success_path_container.visibility = View.VISIBLE
            forecast_list.visibility = View.INVISIBLE
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
