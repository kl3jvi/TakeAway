package com.example.takeaway.data.db

import androidx.room.*
import com.example.takeaway.data.model.RestaurantFeed

@Dao
interface RestaurantFeedDao {

    @Query("SELECT * FROM restaurantFeed")
    suspend fun getRestaurantList(): List<RestaurantFeed>

    @Transaction
    @Query("SELECT * FROM restaurantFeed WHERE name = :name")
    suspend fun getRestaurantDetails(name: String): RestaurantFeed

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurants(restaurantFeed: List<RestaurantFeed>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurantDetails(restaurant: RestaurantFeed)
}