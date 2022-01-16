package com.example.takeaway.data.db

import androidx.room.TypeConverter
import com.example.takeaway.data.model.SortingValues
import com.google.gson.Gson

class Converters {
    val gson = Gson()

    @TypeConverter
    fun stringToSortingValues(sortingObject: String): SortingValues {
        return gson.fromJson(sortingObject, SortingValues::class.java)
    }

    @TypeConverter
    fun sortingValuesToString(sortingValues: SortingValues): String {
        return gson.toJson(sortingValues)
    }
}