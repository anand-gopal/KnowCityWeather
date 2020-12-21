package com.ana.knowcityweather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ana.knowcityweather.data.CityRepository
import com.ana.knowcityweather.model.CityModel
import com.ana.knowcityweather.model.WeatherModel

class CityViewModel : ViewModel() {

    private val mCityListLiveData = MutableLiveData<List<Any>>()
    private val mCityForecastLiveData = MutableLiveData<WeatherModel>()

    fun getCityLiveData(): LiveData<List<Any>> {
        return mCityListLiveData;
    }

    fun getCityForecastLiveData(): LiveData<WeatherModel> {
        return mCityForecastLiveData
    }

    fun fetchCityList(context: Context){
        val fetchCityList = CityRepository.fetchCityList(context)
        mCityListLiveData.postValue(fetchCityList)
    }

    fun bookmarkCity(context: Context, cityModel: CityModel){
        CityRepository.bookmarkCity(context, cityModel)
    }

    fun getCityForecast(context: Context, cityName: String){
        CityRepository.getCityForecast(context, cityName, mCityForecastLiveData)
    }


}