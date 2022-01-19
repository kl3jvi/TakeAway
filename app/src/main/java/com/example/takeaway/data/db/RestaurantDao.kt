package com.example.takeaway.data.db

import androidx.room.*
import com.example.takeaway.data.model.RestaurantFeed
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurantFeed")
    suspend fun getRestaurantList(): List<RestaurantFeed>

    @Transaction
    @Query("SELECT * FROM restaurantFeed WHERE name = :name")
    suspend fun getRestaurantDetails(name: String): RestaurantFeed

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurantFeed(restaurantFeed: List<RestaurantFeed>)

    @Query("SELECT * FROM restaurantFeed ORDER BY CASE WHEN favorite THEN favorite END DESC, CASE WHEN sorting_minCost THEN sorting_minCost END DESC")
    fun sortByMinCost(): Flow<List<RestaurantFeed>>

    @Query("SELECT * FROM restaurantFeed ORDER BY CASE WHEN favorite THEN favorite END DESC, CASE WHEN sorting_bestMatch THEN sorting_bestMatch END DESC")
    fun getBestMatchRestaurants(): Flow<List<RestaurantFeed>>

    @Query("SELECT * FROM restaurantFeed ORDER BY CASE WHEN favorite THEN favorite END DESC, CASE WHEN status = 'open' THEN status END DESC, CASE WHEN status = 'order ahead' THEN status END DESC")
    fun sortByStatus(): List<RestaurantFeed>

    @Query("SELECT * FROM restaurantFeed WHERE id=:id")
    fun getRestaurant(id: Int): Flow<RestaurantFeed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurantFeed: RestaurantFeed)
}