package com.example.moviehub.util

import androidx.room.TypeConverter
import com.example.moviehub.data.model.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    private val gson = Gson()
    @TypeConverter
    fun fromFloatToNumber(float: Float?): Number {
        return float as Number
    }

    @TypeConverter
    fun fromNumberToFloat(number: Number?): Float {
        if (number != null && !number.toString().contains("%")) {
            return number.toFloat()
        }
        return Float.NaN
    }

    @TypeConverter
    fun stringToSomeObjectList(countries: String?): List<Country?>? {
        val listType: Type = object : TypeToken<List<Country?>?>() {}.type
        return gson.fromJson<List<Country?>>(countries, listType)
    }

    @TypeConverter
    fun fromCountryToString(countries: List<Country>): String? {
        return gson.toJson(countries)
    }
}
