package com.ana.knowcityweather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CityViewModel : ViewModel() {

    private val mCityLiveData = MutableLiveData<ArrayList<Any>>()

    fun getCityLiveData(): LiveData<ArrayList<Any>> {
        return mCityLiveData;
    }

    fun setCityList(list: ArrayList<Any>){
        mCityLiveData.value = list
    }

    fun fetchCityList(context: Context){

    }
}