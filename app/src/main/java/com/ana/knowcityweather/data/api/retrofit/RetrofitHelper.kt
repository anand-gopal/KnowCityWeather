package com.ana.knowcityweather.data.api.retrofit

import android.content.Context
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper(context: Context?) {
    val apiInterface: APIInterface
        get() {
            val gson = GsonBuilder()
                .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory<Any?>())
                .create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(APIInterface::class.java)
        }

    companion object {
        const val BASE_URL = "http://api.openweathermap.org/"

        /**
         * @param context
         * @return Instance of this class
         * create instance of this class
         */
        fun with(context: Context?): RetrofitHelper {
            return RetrofitHelper(context)
        }
    }
}