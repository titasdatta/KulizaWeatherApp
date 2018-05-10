package com.titas.weatherappkuliza.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Titas on 5/9/2018.
 */

data class Location(@SerializedName("name") val cityName: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cityName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}

data class Condition(@SerializedName("conditionText") val conditionText: String,
                     @SerializedName("iconLink") val iconLink: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(conditionText)
        parcel.writeString(iconLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Condition> {
        override fun createFromParcel(parcel: Parcel): Condition {
            return Condition(parcel)
        }

        override fun newArray(size: Int): Array<Condition?> {
            return arrayOfNulls(size)
        }
    }
}

data class Day(@SerializedName("maxtemp_c") val maxTemp: Float,
               @SerializedName("mintemp_c") val minTemp: Float,
               @SerializedName("condition") val condition: Condition) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readParcelable(Condition::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(maxTemp)
        parcel.writeFloat(minTemp)
        parcel.writeParcelable(condition, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Day> {
        override fun createFromParcel(parcel: Parcel): Day {
            return Day(parcel)
        }

        override fun newArray(size: Int): Array<Day?> {
            return arrayOfNulls(size)
        }
    }
}

data class WeatherForecast(@SerializedName("date") val forecastDate: String,
                           @SerializedName("day") val day: Day) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Day::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(forecastDate)
        parcel.writeParcelable(day, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherForecast> {
        override fun createFromParcel(parcel: Parcel): WeatherForecast {
            return WeatherForecast(parcel)
        }

        override fun newArray(size: Int): Array<WeatherForecast?> {
            return arrayOfNulls(size)
        }
    }
}

data class Forecast(@SerializedName("forecastday") val forecastList: ArrayList<WeatherForecast>) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(WeatherForecast.CREATOR)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(this.forecastList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Forecast> {
        override fun createFromParcel(parcel: Parcel): Forecast {
            return Forecast(parcel)
        }

        override fun newArray(size: Int): Array<Forecast?> {
            return arrayOfNulls(size)
        }
    }
}

data class Error(@SerializedName("message") val errorMessage: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(errorMessage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Error> {
        override fun createFromParcel(parcel: Parcel): Error {
            return Error(parcel)
        }

        override fun newArray(size: Int): Array<Error?> {
            return arrayOfNulls(size)
        }
    }
}

data class WeatherResponse(@SerializedName("location") val location:Location,
                           @SerializedName("forecast") val forecast: Forecast,
                           @SerializedName("error") val error: Error) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Location::class.java.classLoader),
            parcel.readParcelable(Forecast::class.java.classLoader),
            parcel.readParcelable(Error::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(location, flags)
        parcel.writeParcelable(forecast, flags)
        parcel.writeParcelable(error, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherResponse> {
        override fun createFromParcel(parcel: Parcel): WeatherResponse {
            return WeatherResponse(parcel)
        }

        override fun newArray(size: Int): Array<WeatherResponse?> {
            return arrayOfNulls(size)
        }
    }
}
