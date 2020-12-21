package com.ana.knowcityweather.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ana.knowcityweather.data.api.retrofit.RetrofitHelper
import com.ana.knowcityweather.data.db.AppDatabase
import com.ana.knowcityweather.data.db.CityDao
import com.ana.knowcityweather.data.db.RoomDbHelper
import com.ana.knowcityweather.model.CityModel
import com.ana.knowcityweather.model.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CityRepository {

    private const val API_KEY = "f911fa88cfc4b8b8d760d7c2fdc042e7"

    suspend fun fetchCityList(context: Context): List<CityModel> {
        return getCityDao(context).getAllCities()
    }

    suspend fun bookmarkCity(context: Context, cityModel: CityModel){
        getCityDao(context).addCity(cityModel)
    }

    suspend fun updateCity(context: Context, cityModel: CityModel){
        return getCityDao(context).updateCity(cityModel)
    }

    suspend fun deleteCity(context: Context, cityModel: CityModel){
        return getCityDao(context).deleteCity(cityModel)
    }

    private fun getCityDao(context: Context): CityDao {
        return RoomDbHelper.getDatabase(context).cityDao()
    }

    fun getCityForecast(
        context: Context?,
        city: String?,
        liveData: MutableLiveData<WeatherModel>
    ) {
        RetrofitHelper.with(context)
            .apiInterface
            .getWeatherData(city, API_KEY)
            .enqueue(object : Callback<WeatherModel?> {
                override fun onResponse(
                    call: Call<WeatherModel?>,
                    response: Response<WeatherModel?>
                ) {
                    Log.v("Request ", call.request().url().toString())
                    Log.v("Response ", response.toString())
                    if(response.isSuccessful) {
                        val weatherModel: WeatherModel? = response.body()
                        liveData.value = weatherModel
                    } else {
                        liveData.value = null
                    }
                }

                override fun onFailure(
                    call: Call<WeatherModel?>,
                    t: Throwable
                ) {
                    Log.e("Error Response","${call.request().url()} ${t.message}".trimIndent())
                }
            })
    }
}