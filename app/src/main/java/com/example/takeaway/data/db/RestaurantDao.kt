package com.example.takeaway.data.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.takeaway.data.model.RestaurantFeed
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurantFeed(restaurantFeed: List<RestaurantFeed>)

    @RawQuery(observedEntities = [RestaurantFeed::class])
    fun getRestaurantFeed(query: SupportSQLiteQuery): Flow<List<RestaurantFeed>>

    @Update
    suspend fun updateRestaurant(restaurantFeed: RestaurantFeed): Int

    @Query("SELECT * FROM restaurantFeed WHERE id=:restaurantId")
    fun getRestaurantDetails(restaurantId: Int): RestaurantFeed
}