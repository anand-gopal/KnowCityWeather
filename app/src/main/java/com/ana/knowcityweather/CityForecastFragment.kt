package com.ana.knowcityweather

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ana.knowcityweather.model.CityModel
import com.ana.knowcityweather.model.WeatherModel
import com.ana.knowcityweather.utils.Constants
import kotlinx.android.synthetic.main.layout_forecast.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CityForecastFragment : BaseFragment() {

    private lateinit var cityViewModel: CityViewModel
    private var weatherModel: WeatherModel? = null

    override fun init(view: View?) {
        loader.visibility = View.VISIBLE
        mainContainer.visibility = View.GONE
        val existing = arguments?.getBoolean(Constants.EXISTING)
        bookmark.visibility =
            if (existing == true) View.GONE else View.VISIBLE
        bookmark.setOnClickListener {
            context?.applicationContext?.let { it1 ->
                weatherModel?.city?.id?.let { it2 ->
                    cityViewModel.bookmarkCity(
                        it1, CityModel(
                            weatherModel?.city?.country,
                            null,
                            weatherModel?.city?.name,
                            it2,
                            "",
                            weatherModel?.list?.get(0)?.main?.temp,
                            weatherModel?.city?.coord?.lon,
                            weatherModel?.city?.coord?.lat
                        )
                    )
                    Toast.makeText(context, "Bookmarked successful", Toast.LENGTH_LONG).show()
                }
            }

        }
        cityViewModel = ViewModelProvider(
            activity!!,
            ViewModelProvider.NewInstanceFactory()
        ).get(CityViewModel::class.java)
        cityViewModel.getCityForecastLiveData().observe(activity!!, Observer { model ->
            /* Populating extracted data into our views */
            try {
                weatherModel = model
                updateDataInUI(model)
                if(existing == true) {
                    updateCityData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    private fun updateCityData() {
        weatherModel?.city?.id?.let { it ->
            val cityModel = CityModel(
                weatherModel?.city?.country,
                null,
                weatherModel?.city?.name,
                it,
                "",
                weatherModel?.list?.get(0)?.main?.temp,
                weatherModel?.city?.coord?.lon,
                weatherModel?.city?.coord?.lat
            )
            cityViewModel.updateCityData(context?.applicationContext, cityModel)
        }
    }

    private fun updateDataInUI(model: WeatherModel?) {
        model?.apply {
            address.text = "${city?.name} - ${city?.country}"
            updated_at.text =
                "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(list?.get(0)?.dt?.toLong()?.times(1000) ?: 0)
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
            mainContainer.visibility = View.VISIBLE
            errorText.visibility = View.GONE

        }
        loader.visibility = View.GONE
        if (model == null) {
            errorText.visibility = View.VISIBLE
            mainContainer.visibility = View.GONE
            bookmark.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.applicationContext?.let {
            val city = arguments?.getString(Constants.CITY)
            city?.let { it1 ->
                cityViewModel.getCityForecast(
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