package com.example.takeaway.data.restaurant

import com.example.takeaway.data.RestaurantFeedJsonParser
import com.example.takeaway.data.db.RestaurantDao
import com.example.takeaway.data.model.RestaurantFeed
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RestaurantFeedRepository @Inject constructor(
    private val dao: RestaurantDao,
    private val provideIoDispatcher: CoroutineDispatcher
) {

    suspend fun fetchRestaurantFeed() {
        val restaurants = RestaurantFeedJsonParser.getSampleRestaurantList().restaurants
        dao.insertRestaurantFeed(restaurants)
    }

    suspend fun setFavorite(restaurantFeed: RestaurantFeed) = dao.insertRestaurant(restaurantFeed)

    fun sortByMinCost() = dao.sortByMinCost()
    fun sortByBestMatch() = dao.getBestMatchRestaurants()
    fun sortByStatus() = dao.sortByStatus()
}