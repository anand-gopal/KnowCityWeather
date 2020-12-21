package com.ana.knowcityweather.model

import android.os.Parcel
import android.os.Parcelable

class CityModel(var country: String? = null,
                var coord: Coord? = null,
                var name: String? = null,
                var id: Int = 0,
                var state: String? = null,
                var prevTemp: Double? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Coord::class.java.classLoader),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    data class Coord(var lon: Double = 0.0,
                     var lat: Double = 0.0) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble()
        )
        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeDouble(lon)
            parcel.writeDouble(lat)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Coord> {
            override fun createFromParcel(parcel: Parcel): Coord {
                return Coord(parcel)
            }

            override fun newArray(size: Int): Array<Coord?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(country)
        parcel.writeParcelable(coord, flags)
        parcel.writeString(name)
        parcel.writeInt(id)
        parcel.writeString(state)
        parcel.writeValue(prevTemp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityModel> {
        override fun createFromParcel(parcel: Parcel): CityModel {
            return CityModel(parcel)
        }

        override fun newArray(size: Int): Array<CityModel?> {
            return arrayOfNulls(size)
        }
    }
}