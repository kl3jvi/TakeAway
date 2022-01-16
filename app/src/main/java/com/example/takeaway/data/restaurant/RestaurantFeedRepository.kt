package com.example.takeaway.data.restaurant

import com.example.takeaway.data.RestaurantFeedJsonParser
import com.example.takeaway.data.api.Result
import com.example.takeaway.data.db.RestaurantFeedDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestaurantFeedRepository @Inject constructor(
    private val dao: RestaurantFeedDao,
    private val provideIoDispatcher: CoroutineDispatcher
) {
    suspend fun getRestaurantFeedList() = withContext(provideIoDispatcher) {
        flow {
            val restaurantList = dao.getRestaurantList()
            if (restaurantList.isNotEmpty()) {
                emit(Result.Success(restaurantList))
            } else {
                try {
                    val restaurants = RestaurantFeedJsonParser.getSampleRestaurantList().restaurants
                    dao.insertAllRestaurants(restaurants)
                    emit(Result.Success(restaurants))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
        }
    }
}