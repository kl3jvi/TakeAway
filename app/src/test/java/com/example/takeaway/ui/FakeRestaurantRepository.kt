package com.example.takeaway.ui

import com.example.takeaway.data.api.Result
import com.example.takeaway.data.model.RestaurantFeed
import com.example.takeaway.data.restaurant.RestaurantRepository
import com.example.takeaway.util.TestData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeRestaurantRepository : RestaurantRepository {
    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun fetchRestaurantFeed() {

    }

    override fun getSortedRestaurants(filter: String): Result<Flow<List<RestaurantFeed>>> {
        return if (shouldReturnError) {
            Result.Error(Exception("No data"))
        } else {
            val list: Flow<List<RestaurantFeed>> = flowOf(TestData.restaurantList)
            Result.Success(list)
        }
    }

    override fun getRestaurantDetails(id: Int): Flow<Result<RestaurantFeed>> = flow {
        if (shouldReturnError) {
            emit(Result.Error(Exception("No data")))
        } else {
            emit(Result.Success(TestData.restaurant1))
        }
    }

    override suspend fun setFavorite(restaurantFeed: RestaurantFeed): Result<Int> {
        return if (shouldReturnError) {
            Result.Error(Exception("No data"))
        } else {
            Result.Success(1)
        }
    }
}