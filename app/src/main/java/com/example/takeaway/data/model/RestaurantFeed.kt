package com.example.takeaway.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "RestaurantFeed")
data class RestaurantFeed(
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,
    val name: String,
    val status: String
)