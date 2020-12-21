package com.ana.knowcityweather

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.navigation.fragment.findNavController
import com.ana.knowcityweather.model.CityModel
import com.ana.knowcityweather.utils.CityHelper
import com.ana.knowcityweather.utils.Constants
import com.ana.knowcityweather.utils.hideKeyboard
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_add_city.*


class AddCityFragment:BaseFragment() {

    private var selectedCityModel: CityModel? = null

    override fun init(view: View?) {
        searchView.queryHint = getString(R.string.search_city)
        searchView.findViewById<ImageView>(R.id.search_button)
            .setColorFilter(ContextCompat.getColor(activity?.applicationContext!!, R.color.colorText), PorterDuff.Mode.MULTIPLY);

        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(context, R.layout.search_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        val suggestions = CityHelper.getSupportedCityList(activity?.applicationContext)

        searchView.suggestionsAdapter = cursorAdapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                findNavController().navigate(R.id.cityForecastFragment,
                    bundleOf(Constants.CITY to (query?.split("-")?.get(0) ?: "")))
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))

                Observable
                    .fromIterable(suggestions)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation())
                    .filter { city ->
                        if (query?.let { city.name?.contains(it, true) } == true) {
                            cursor.addRow(arrayOf(city.id, city.name +" - "+city.country))
                            true
                        } else
                            false
                    }
                    .toList()
                    .subscribe(object : SingleObserver<List<CityModel?>> {
                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onSuccess(userModels: List<CityModel?>) {
                            cursorAdapter.changeCursor(cursor)
                        }

                        override fun onError(e: Throwable) {
                            Log.e("Error", e.message)
                        }
                    })
                return true
            }
        })

        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, true)
                return true
            }

        })
    }

    override fun getResId(): Int {
        return R.layout.layout_add_city
    }
}