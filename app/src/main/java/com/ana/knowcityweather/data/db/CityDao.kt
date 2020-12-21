package com.ana.knowcityweather.data.db

import androidx.room.*
import com.ana.knowcityweather.model.CityModel

@Dao
interface CityDao {

    @Query("Select * from city")
    fun getAllCities(): List<CityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(cityModel: CityModel)

    @Delete
    fun deleteCity(cityModel: CityModel)

    @Update
    fun updateCity(cityModel: CityModel)
}