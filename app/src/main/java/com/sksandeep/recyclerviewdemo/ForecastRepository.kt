package com.sksandeep.recyclerviewdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

/*What Repository does?
Loading data for us and provide that data out for activity*/
class ForecastRepository {

    //How the activity get data from repository?
    //we define a property that use's to update the data

    //we define _weeklyForecast that return a week worth temp data from DailyForeCast
    //we use mutableLiveData for data holding
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()

    //for activity to listen that update so we create public LiveData
    //we don't want our _weeklyForecast data to change from other place
    val weeklyForecast:LiveData<List<DailyForecast>> =_weeklyForecast

    //we need to lode the data
    fun loadForecast(zipcode:String){
        //genrate random temp between 1 to 100
        val randomValue = List(15){ Random.nextFloat().rem(100)*100 }

        //we have a list of random temp with same description
        val forecastItems = randomValue.map { temp->
            DailyForecast(temp,getTempdescription(temp))
        }

        //send forecastItems list to _weeklyForecast
        _weeklyForecast.setValue(forecastItems)
    }

    private fun getTempdescription(temp:Float):String{

        return when(temp){
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything low 0 doesnot make sense"
            in 0f.rangeTo(32f)->"may too cold"
            in 32f.rangeTo(55f)->"that what i prefer"
            in 55f.rangeTo(100f)->"getting warm"
            else -> "Does not compure"
        }

    }
}