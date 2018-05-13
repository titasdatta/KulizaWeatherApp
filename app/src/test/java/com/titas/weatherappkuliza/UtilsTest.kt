package com.titas.weatherappkuliza

import android.content.Context
import android.text.format.DateUtils
import com.titas.weatherappkuliza.common.Utils
import junit.framework.Assert
import okhttp3.internal.Util
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*

/**
 * Created by Titas on 5/13/2018.
 */

@RunWith(JUnit4::class)
class UtilsTest{
    private lateinit var utils: Utils



    @Before
    fun setup(){
        val context = mock<Context>(Context::class.java)
        utils = Utils(context)
    }

    @Test
    fun getWeatherConditionTest(){

        Assert.assertEquals("Sunny", utils.getWeatherCondition("Sunny"))
        Assert.assertEquals("Rain", utils.getWeatherCondition("Patchy rain possible"))
        Assert.assertEquals("Rain", utils.getWeatherCondition("Moderate or heavy freezing rain"))
        Assert.assertEquals("Snow", utils.getWeatherCondition("Moderate or heavy showers of ice pellets"))
        Assert.assertEquals("Snow", utils.getWeatherCondition("Blowing snow"))
        Assert.assertEquals("Sleet", utils.getWeatherCondition("Patchy sleet possible"))
        Assert.assertEquals("Sleet", utils.getWeatherCondition("Moderate or heavy sleet showers"))
        Assert.assertEquals("Fog", utils.getWeatherCondition("Freezing fog"))
        Assert.assertEquals("Some random string", utils.getWeatherCondition("Some random string"))

    }

    @Test
    fun isTomorrowsDateTest(){
        var date = Calendar.getInstance()
        date.add(Calendar.DATE, 1)
        Assert.assertEquals(true, utils.isTomorrowsDate(Date(date.timeInMillis)))

        date = Calendar.getInstance()
        date.add(Calendar.DATE, -5)
        Assert.assertEquals(false, utils.isTomorrowsDate(Date(date.timeInMillis)))

        date = Calendar.getInstance()
        Assert.assertEquals(false, utils.isTomorrowsDate(Date(date.timeInMillis)))
    }
}