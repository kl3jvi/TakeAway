package com.example.takeaway.data.restaurant

import com.example.takeaway.data.api.Result
import com.example.takeaway.data.db.RestaurantFeedDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestaurantDetailsRepository @Inject constructor(
    private val dao: RestaurantFeedDao,
    private val provideIoDispatcher: CoroutineDispatcher
) {

    suspend fun getRestaurantDetails(restaurantName: String) = withContext(provideIoDispatcher) {
        flow {
            val restaurantDetails = dao.getRestaurantDetails(restaurantName)
            emit(Result.Success(restaurantDetails))
        }
    }
}