package com.titas.weatherappkuliza.common

/**
 * Created by Titas on 5/9/2018.
 */
class Constants {
    companion object {
        val API_KEY: String = "f8ea755e72754a7882f92757180805"
        val BASE_URL: String = "http://api.apixu.com/v1/"
        val CONDITION_TEXT_MAP: HashMap<String, List<String>> = HashMap()

        init {
            CONDITION_TEXT_MAP.put("Sunny", arrayListOf("Sunny"))

            CONDITION_TEXT_MAP.put("Rain", arrayListOf("Rain","Patchy rain possible",
                    "Patchy freezing drizzle possible","Patchy light drizzle","Light drizzle",
                    "Freezing drizzle","Heavy freezing drizzle","Patchy light rain","Light rain",
                    "Moderate rain at times","Moderate rain","Heavy rain at times","Heavy rain",
                    "Light freezing rain","Moderate or heavy freezing rain",
                    "Moderate or heavy rain shower","Torrential rain shower", "Light rain shower"))

            CONDITION_TEXT_MAP.put("Snow",arrayListOf(
                    "Moderate or heavy showers of ice pellets",
                    "Moderate or heavy snow showers","Light showers of ice pellets","Light snow showers",
                    "Heavy snow","Patchy heavy snow","Moderate snow","Patchy moderate snow","Light snow",
                    "Patchy light snow","Blowing snow","Patchy snow possible","Ice pellets"))

            CONDITION_TEXT_MAP.put("Sleet", arrayListOf("Patchy sleet possible","Light sleet",
                    "Moderate or heavy sleet","Light sleet showers", "Moderate or heavy sleet showers"))

            CONDITION_TEXT_MAP.put("Storm", arrayListOf("Thundery outbreaks possible",
                    "Patchy light rain with thunder", "Moderate or heavy rain with thunder",
                    "Moderate or heavy snow with thunder", "Patchy light snow with thunder"))

            CONDITION_TEXT_MAP.put("Fog", arrayListOf("Fog","Freezing fog"))

            CONDITION_TEXT_MAP.put("Clear", arrayListOf())

            CONDITION_TEXT_MAP.put("Cloudy", arrayListOf("Partly cloudy"))



        }
    }
}