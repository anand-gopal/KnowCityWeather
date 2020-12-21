package com.ana.knowcityweather.data.db

import androidx.room.*
import com.ana.knowcityweather.model.CityModel

@Dao
interface CityDao {

    @Query("Select * from city")
    suspend fun getAllCities(): List<CityModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCity(cityModel: CityModel)

    @Delete
    suspend fun deleteCity(cityModel: CityModel)

    @Update
    suspend fun updateCity(cityModel: CityModel)
}