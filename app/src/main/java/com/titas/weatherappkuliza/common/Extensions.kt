package com.titas.weatherappkuliza.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.annotation.LayoutRes
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*
import java.util.logging.Handler

/**
 * Created by Titas on 5/12/2018.
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.load(path: String) {
    Picasso.get().load(path).into(this)
}

fun String.getWeatherCondition(): String {
    if(this.split(" ").size == 1){
        return this
    }else {
        Constants.CONDITION_TEXT_MAP.forEach {
            if(it.value.contains(this)) return it.key
        }
    }

    return this
}

fun Date.isTomorrow(): Boolean {
    val tomorrowsDate = Calendar.getInstance()
    tomorrowsDate.add(Calendar.DATE, 1)
    val dateToBeValidated = Calendar.getInstance()
    dateToBeValidated.time = this
    return ((tomorrowsDate.get(Calendar.YEAR) == dateToBeValidated.get(Calendar.YEAR)) &&
            (tomorrowsDate.get(Calendar.MONTH)) == dateToBeValidated.get(Calendar.MONTH) &&
            (tomorrowsDate.get(Calendar.DAY_OF_MONTH) == dateToBeValidated.get(Calendar.DAY_OF_MONTH)))
}

fun String.getFormattedDate(): String{
    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val date = simpleDateFormat.parse(this)
    if(DateUtils.isToday(date.time)) return "Today"
    else if(date.isTomorrow()) return "Tomorrow"
    else {
        simpleDateFormat = SimpleDateFormat("dd MMM yyyy")
        return simpleDateFormat.format(date)
    }
}

fun ConnectivityManager.isNetworkAvailable(): Boolean {
    val networkInfo: NetworkInfo? = this.activeNetworkInfo
    return networkInfo?.isConnected ?: false
}