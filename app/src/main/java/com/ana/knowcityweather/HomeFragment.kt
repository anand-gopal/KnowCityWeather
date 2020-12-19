package com.ana.knowcityweather

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ana.knowcityweather.data.City
import com.ana.knowcityweather.utils.inflate
import kotlinx.android.synthetic.main.city_item.view.*
import kotlinx.android.synthetic.main.layout_home.*

class HomeFragment : BaseFragment() {
    private lateinit var cityModel: CityViewModel
    private val cityList = ArrayList<Any>()

    override fun init(view: View?) {
        cityModel = ViewModelProvider(
            activity!!,
            ViewModelProvider.NewInstanceFactory()
        ).get(CityViewModel::class.java)
        cityModel.setCityList(cityList)
        cityRecycler.layoutManager = LinearLayoutManager(activity?.applicationContext)
        val cityAdapter = CityAdapter(activity?.applicationContext, cityList)
        cityRecycler.adapter = cityAdapter
        cityModel.getCityLiveData().observe(activity!!, Observer { list->
            cityAdapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        activity?.applicationContext?.let { cityModel.fetchCityList(it) }
    }

    override fun getResId(): Int {
        return R.layout.layout_home
    }

    class CityAdapter(val context: Context?,val list: ArrayList<Any>) :
        RecyclerView.Adapter<CityAdapter.CityHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
            val inflatedView = parent.inflate(R.layout.city_item, false)
            return CityHolder(inflatedView)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: CityHolder, position: Int) {
            holder.bind(list[position])
        }

        class CityHolder(private var view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

            private var city: City? = null

            init {
                view.setOnClickListener(this)
            }

            override fun onClick(v: View) {
                //TODO
            }

            fun bind(city: Any?){
                this.city = city as City?
                view.name.text = city?.name
                view.temp.text = city?.prevTemp.toString()
            }

        }

    }

}