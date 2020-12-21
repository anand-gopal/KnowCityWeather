package com.ana.knowcityweather.data.db

import android.content.Context
import androidx.room.Room

object RoomDbHelper {

    private var appDatabase: AppDatabase? = null

    fun getDatabase(applicationContext: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "app-database"
            ).build()
        }
        return appDatabase!!
    }
}