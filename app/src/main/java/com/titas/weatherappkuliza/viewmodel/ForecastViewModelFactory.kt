package com.titas.weatherappkuliza.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Singleton
import javax.inject.Provider

/**
 * Created by Titas on 5/13/2018.
 */
@Singleton
class ForecastViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>?): ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator = creators!![modelClass]
        if(creator == null){
            for(entry in creators.entries){
                if(modelClass.isAssignableFrom(entry.key)){
                    creator = entry.value
                    break
                }
            }
        }
        if(creator == null){
            throw IllegalArgumentException("unknown model class ${modelClass}")
        }
        try{
            return creator.get() as T
        }catch (e: Exception){
            throw RuntimeException(e)
        }
    }
}