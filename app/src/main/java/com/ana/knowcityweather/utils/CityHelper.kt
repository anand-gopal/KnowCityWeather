package com.ana.knowcityweather.utils

import android.content.Context
import com.ana.knowcityweather.model.CityModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.Charset


object CityHelper {

    /*fun fetchMatchingCities(context: Context, searchText: String, observer: SingleObserver<ArrayList<City>>){
        val jsonArray = readJSONFromAsset(context, "city_list.json")
        val gson = Gson()
        val listUserType: Type = object :
            TypeToken<List<City>?>() {}.type

        val cities: List<City> =
            gson.fromJson(jsonArray, listUserType)
        cities.toFlowable()
            .filter{ it.name?.startsWith(searchText)?:false}
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }*/

    fun getSupportedCityList(context: Context?) : List<CityModel?>? {
        val jsonArray = context?.let { readJSONFromAsset(it, "city_list.json") }
        val gson = Gson()
        val listUserType: Type = object :
            TypeToken<List<CityModel>?>() {}.type
        return gson.fromJson(jsonArray, listUserType)
    }


    private fun readJSONFromAsset(context: Context, fileName: String): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }
}