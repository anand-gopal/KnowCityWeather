package com.ana.knowcityweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ana.knowcityweather.model.CityModel

@Database(entities = [CityModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
}