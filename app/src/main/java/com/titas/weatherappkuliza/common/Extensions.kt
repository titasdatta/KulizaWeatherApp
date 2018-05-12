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
