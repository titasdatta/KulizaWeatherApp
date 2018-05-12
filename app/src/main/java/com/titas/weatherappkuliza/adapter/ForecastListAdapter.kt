package com.titas.weatherappkuliza.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.titas.weatherappkuliza.R
import com.titas.weatherappkuliza.common.getFormattedDate
import com.titas.weatherappkuliza.common.getWeatherCondition
import com.titas.weatherappkuliza.common.inflate
import com.titas.weatherappkuliza.common.load
import com.titas.weatherappkuliza.model.WeatherForecast
import kotlinx.android.synthetic.main.forecast_row_item.view.*


/**
 * Created by Titas on 5/12/2018.
 */
class ForecastListAdapter(private val forecastList: ArrayList<WeatherForecast>): RecyclerView.Adapter<ForecastListAdapter.ForecastListViewHolder>() {

    override fun onBindViewHolder(holder: ForecastListViewHolder?, position: Int) {
        val forecast = forecastList[position]
        holder?.let {
            holder.bind(forecast)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder {
        val rowView = parent.inflate(R.layout.forecast_row_item, false)
        return ForecastListViewHolder(rowView)
    }

    override fun getItemCount(): Int = forecastList.size

    class ForecastListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var mView: View = view
        private var forecast: WeatherForecast? = null

        fun bind(forecast: WeatherForecast){
            this.forecast = forecast
            mView.condition_icon.load("https:${forecast.day.condition.iconLink}")
            val max_min_temp = "${forecast.day.minTemp}"+"°"+"/"+"${forecast.day.maxTemp}"+"°"
            mView.max_min_temp.text = Html.fromHtml(max_min_temp)
            mView.condition_text.text = forecast.day.condition.conditionText.getWeatherCondition()
            mView.date.text = forecast.forecastDate.getFormattedDate()
        }
    }
}