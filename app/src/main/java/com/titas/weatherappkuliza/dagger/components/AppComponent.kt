package com.titas.weatherappkuliza.dagger.components

import com.titas.weatherappkuliza.dagger.WeatherApplication
import com.titas.weatherappkuliza.dagger.modules.AppModule
import com.titas.weatherappkuliza.dagger.modules.NetModule
import com.titas.weatherappkuliza.dagger.modules.ViewModelModule
import com.titas.weatherappkuliza.view.WeatherActivity
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Titas on 5/13/2018.
 */

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class ,ViewModelModule::class, AppModule::class, NetModule::class))
interface  AppComponent{
    fun inject(application: WeatherApplication)
    fun inject(activity: WeatherActivity)
}