package com.titas.weatherappkuliza.dagger

import android.app.Activity
import android.app.Application
import com.titas.weatherappkuliza.common.Constants
import com.titas.weatherappkuliza.dagger.components.AppComponent
import com.titas.weatherappkuliza.dagger.components.DaggerAppComponent
import com.titas.weatherappkuliza.dagger.modules.AppModule
import com.titas.weatherappkuliza.dagger.modules.NetModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/**
 * Created by Titas on 5/13/2018.
 */
class WeatherApplication: Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(Constants.BASE_URL))
                .build()
        component.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}