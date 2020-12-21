package com.ana.knowcityweather.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ana.knowcityweather.data.api.retrofit.RetrofitHelper
import com.ana.knowcityweather.model.WeatherModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CityRepository {

    private const val API_KEY = "f911fa88cfc4b8b8d760d7c2fdc042e7"

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
                    val weatherModel: WeatherModel? = response.body()
                    liveData.value = weatherModel
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