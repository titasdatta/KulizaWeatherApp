package com.titas.weatherappkuliza.common

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Titas on 5/13/2018.
 */
@Singleton
class Utils @Inject constructor(private val context: Context) {
    val locationManager: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun getWeatherCondition(conditionDesc: String): String {
        if(conditionDesc.split(" ").size == 1){
            return conditionDesc
        }else {
            Constants.CONDITION_TEXT_MAP.forEach {
                if(it.value.contains(conditionDesc)) return it.key
            }
        }

        return conditionDesc
    }

    fun isTomorrowsDate(date: Date): Boolean {
        val tomorrowsDate = Calendar.getInstance()
        tomorrowsDate.add(Calendar.DATE, 1)
        val dateToBeValidated = Calendar.getInstance()
        dateToBeValidated.time = date
        return ((tomorrowsDate.get(Calendar.YEAR) == dateToBeValidated.get(Calendar.YEAR)) &&
                (tomorrowsDate.get(Calendar.MONTH)) == dateToBeValidated.get(Calendar.MONTH) &&
                (tomorrowsDate.get(Calendar.DAY_OF_MONTH) == dateToBeValidated.get(Calendar.DAY_OF_MONTH)))
    }

    fun getFormattedDateFor(dateString: String): String{
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = simpleDateFormat.parse(dateString)
        if(DateUtils.isToday(date.time)) return "Today"
        else if(isTomorrowsDate(date)) return "Tomorrow"
        else {
            simpleDateFormat = SimpleDateFormat("dd MMM yyyy")
            return simpleDateFormat.format(date)
        }
    }

    fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isAvailable && activeNetwork.isConnected
    }
}