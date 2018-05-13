package com.titas.weatherappkuliza.view

import android.Manifest
import android.animation.Animator
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.TranslateAnimation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.titas.weatherappkuliza.R
import com.titas.weatherappkuliza.adapter.ForecastListAdapter
import com.titas.weatherappkuliza.common.Constants.Companion.LOCATION_REFRESH_DISTANCE
import com.titas.weatherappkuliza.common.Constants.Companion.LOCATION_REFRESH_TIME
import com.titas.weatherappkuliza.common.Constants.Companion.PERMISSION_REQUEST_CODE
import com.titas.weatherappkuliza.common.Utils
import com.titas.weatherappkuliza.dagger.WeatherApplication
import com.titas.weatherappkuliza.model.ForecastWrapper
import com.titas.weatherappkuliza.model.StatusCode
import com.titas.weatherappkuliza.model.WeatherForecast
import com.titas.weatherappkuliza.viewmodel.ForecastListViewModel
import kotlinx.android.synthetic.main.activity_weather.*
import java.lang.Exception
import javax.inject.Inject

class WeatherActivity : AppCompatActivity() {

    private lateinit var weatherResponseLiveData : LiveData<ForecastWrapper>
    private var forecastListAdapter: ForecastListAdapter? = null
    private lateinit var forecastViewModel: ForecastListViewModel
    private var forecastList: ArrayList<WeatherForecast> = arrayListOf()
    @Inject
    lateinit var utils: Utils
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val locationListener: LocationListener = object : LocationListener{
        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onLocationChanged(location: Location?) {
            if(location != null){
                fetchWeatherForLocation(location)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WeatherApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        title = "Kuliza Weather App"

        forecastViewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastListViewModel::class.java)
        weatherResponseLiveData = forecastViewModel.getWeatherObservable()
        weatherResponseLiveData.observe(this, object : Observer<ForecastWrapper>{
            override fun onChanged(t: ForecastWrapper?) {
                hideProgress()
                if(t?.status == StatusCode.OK){
                    updateForecastScreen(t)
                }else{
                    showFailureScreen()
                }
            }
        })

        val layoutManager = LinearLayoutManager(this)
        forecast_list.layoutManager = layoutManager
        forecast_list.setHasFixedSize(true)
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

    private fun updateForecastScreen(forecastData: ForecastWrapper?){
        forecastData?.let {
            val currentTemp = "${forecastData.currentTemp}" + "\u00B0"
            current_temp.text = "${currentTemp}"
            current_location.text = "${forecastData.city}"
            forecastList.clear()
            forecastList.addAll(forecastData.forecastList)
            success_path_container.visibility = View.VISIBLE
            if(forecastListAdapter == null) {
                forecastListAdapter = ForecastListAdapter(forecastList, utils)
                forecast_list.adapter = forecastListAdapter
                forecast_list.visibility = View.INVISIBLE
                //animate list from below
                slideUp()
            }else{
                forecastListAdapter?.notifyDataSetChanged()
            }
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
            setupLocationManager()
        }
    }

    private fun setupLocationManager(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            utils.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, locationListener)
            try {
                val location = utils.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if(location != null){
                    fetchWeatherForLocation(location)
                }
            }catch (e: Exception){
                Log.d("WeatherLogs", "Error fetching last location:${e.message}")
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST_CODE)
        }
    }

    private fun fetchWeatherForLocation(location: Location){
        val queryString = "${location.latitude},${location.longitude}"
        forecastViewModel.getWeather(queryString)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permission granted by user
                    setupLocationManager()
                }else{
                    //permission denied by user
                    //TODO: Or maybe show error screen to block the user from using the app
                    //TODO: without giving location permission
                    forecastViewModel.getWeather("bengaluru")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupLocationManager()
    }

    override fun onPause() {
        super.onPause()
        utils.locationManager.removeUpdates(locationListener)
    }
}
