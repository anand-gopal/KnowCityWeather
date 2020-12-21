package com.ana.knowcityweather.data.api.retrofit

import com.ana.knowcityweather.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface APIInterface {
    @GET("data/2.5/forecast/")
    fun getWeatherData(
        @Query("q") city: String?,
        @Query("appid") appid: String?,
        @Query("units") units: String = "metric"
    ): Call<WeatherModel?>
}