package com.example.takeaway.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.takeaway.data.db.Converters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "restaurantFeed")
@TypeConverters(Converters::class)
data class RestaurantFeed(
    @PrimaryKey
    val name: String,
    val status: String,
    val sortingValues: SortingValues
)

data class SortingValues(
    val bestMatch: Double,
    val newest: Double,
    val ratingAverage: Double,
    val distance: Int,
    val popularity: Double,
    val averageProductPrice: Int,
    val deliveryCosts: Int,
    val minCost: Int,
)