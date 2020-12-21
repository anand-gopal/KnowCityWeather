package com.ana.knowcityweather

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ana.knowcityweather.model.WeatherModel
import com.ana.knowcityweather.utils.Constants
import kotlinx.android.synthetic.main.layout_forecast.*
import java.text.SimpleDateFormat
import java.util.*

class CityForecastFragment : BaseFragment() {

    private lateinit var cityModel: CityViewModel

    override fun init(view: View?) {
        loader.visibility = View.VISIBLE
        mainContainer.visibility = View.GONE
        bookmark.setOnClickListener{

        }
        cityModel = ViewModelProvider(
            activity!!,
            ViewModelProvider.NewInstanceFactory()
        ).get(CityViewModel::class.java)
        cityModel.getCityForecastLiveData().observe(activity!!, Observer { model ->
            /* Populating extracted data into our views */
            try {
                updateDataInUI(model)
            }catch (e:Exception){
                e.printStackTrace()
            }
        })
    }

    private fun updateDataInUI(model: WeatherModel) {
        model.apply {
            address.text = "${city?.name} - ${city?.country}"
            updated_at.text =
                "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(list?.get(0)?.dt?.toLong()?.times(1000)?:0)
                )
            status.text = list?.get(0)?.weather?.get(0)?.description?.capitalize()
            temp.text = list?.get(0)?.main?.temp.toString() + "°C"
            temp_min.text = list?.get(0)?.main?.temp_max.toString() + "°C"
            temp_max.text = list?.get(0)?.main?.temp_max.toString() + "°C"
            sunrise.text = SimpleDateFormat(
                "hh:mm a",
                Locale.ENGLISH
            ).format(Date(city?.sunrise?.toLong()!! * 1000))
            sunset.text = SimpleDateFormat(
                "hh:mm a",
                Locale.ENGLISH
            ).format(Date(city?.sunset?.toLong()!! * 1000))
            wind.text = list?.get(0)?.wind?.speed.toString()
            pressure.text = list?.get(0)?.main?.pressure.toString()
            humidity.text = list?.get(0)?.main?.humidity.toString()
            loader.visibility = View.GONE
            mainContainer.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.applicationContext?.let {
            val city = arguments?.getString(Constants.CITY)
            city?.let { it1 ->
                cityModel.getCityForecast(
                    it,
                    it1
                )
            }
        }
    }

    override fun getResId(): Int {
        return R.layout.layout_forecast
    }
}