package com.titas.weatherappkuliza.dagger

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by Titas on 5/13/2018.
 */
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)