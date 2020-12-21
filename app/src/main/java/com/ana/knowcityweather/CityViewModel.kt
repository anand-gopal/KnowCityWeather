package com.ana.knowcityweather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ana.knowcityweather.data.CityRepository
import com.ana.knowcityweather.model.WeatherModel

class CityViewModel : ViewModel() {

    private val mCityListLiveData = MutableLiveData<ArrayList<Any>>()
    private val mCityForecastLiveData = MutableLiveData<WeatherModel>()

    fun getCityLiveData(): LiveData<ArrayList<Any>> {
        return mCityListLiveData;
    }

    fun setCityList(list: ArrayList<Any>){
        mCityListLiveData.value = list
    }

    fun getCityForecastLiveData(): LiveData<WeatherModel> {
        return mCityForecastLiveData
    }

    fun fetchCityList(context: Context){

    }

    fun getCityForecast(context: Context, cityName: String){
        CityRepository.getCityForecast(context, cityName, mCityForecastLiveData)
    }


}