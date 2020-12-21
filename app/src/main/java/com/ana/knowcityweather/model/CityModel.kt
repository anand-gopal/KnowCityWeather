package com.ana.knowcityweather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "city")
data class CityModel(
    @ColumnInfo(name = "country")
    var country: String? = null,

    @Ignore
    var coord: Coord? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @PrimaryKey
    var id: Int = 0,

    @ColumnInfo(name = "state")
    var state: String? = null,

    @ColumnInfo(name = "prevTemp")
    var prevTemp: Double? = null,

    @ColumnInfo(name = "lon")
    var lon: Double? = coord?.lon,

    @ColumnInfo(name = "lat")
    var lat: Double? = coord?.lat
) {
    data class Coord(
        var lon: Double = 0.0,
        var lat: Double = 0.0
    )

}