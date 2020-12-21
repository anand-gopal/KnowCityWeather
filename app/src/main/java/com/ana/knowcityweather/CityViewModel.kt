package com.ana.knowcityweather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ana.knowcityweather.data.CityRepository
import com.ana.knowcityweather.model.CityModel
import com.ana.knowcityweather.model.WeatherModel
import kotlinx.coroutines.launch

class CityViewModel : ViewModel() {

    private val mCityListLiveData = MutableLiveData<List<Any>>()
    private val mCityForecastLiveData = MutableLiveData<WeatherModel>()

    fun getCityLiveData(): LiveData<List<Any>> {
        return mCityListLiveData;
    }

    fun getCityForecastLiveData(): LiveData<WeatherModel> {
        return mCityForecastLiveData
    }

    suspend fun fetchCityList(context: Context) {
        val fetchCityList = CityRepository.fetchCityList(context)
        mCityListLiveData.postValue(fetchCityList)
    }

    fun bookmarkCity(context: Context, cityModel: CityModel) {
        viewModelScope.launch {
            CityRepository.bookmarkCity(context, cityModel)
            println("bookmarked success")
        }
    }

    fun getCityForecast(context: Context, cityName: String) {
        CityRepository.getCityForecast(context, cityName, mCityForecastLiveData)
    }

    fun updateCityData(context: Context?, cityModel: CityModel) {
        viewModelScope.launch {
            context?.let {
                CityRepository.updateCity(it, cityModel)
                println("updated success")
            }

        }
    }


}