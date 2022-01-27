package com.example.takeaway.data.restaurant

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.takeaway.data.RestaurantFeedJsonParser
import com.example.takeaway.data.api.Result
import com.example.takeaway.data.db.RestaurantDao
import com.example.takeaway.data.model.RestaurantFeed
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RestaurantRepository {
    suspend fun fetchRestaurantFeed()

    fun getSortedRestaurants(filter: String): Result<Flow<List<RestaurantFeed>>>

    fun getRestaurantDetails(id: Int): Flow<Result<RestaurantFeed>>

    suspend fun setFavorite(restaurantFeed: RestaurantFeed): Result<Int>
}

class RestaurantRepositoryImpl @Inject constructor(
    private val dao: RestaurantDao,
    private val provideIoDispatcher: CoroutineDispatcher
) : RestaurantRepository {

    override suspend fun fetchRestaurantFeed() = withContext(provideIoDispatcher) {
        val restaurants = RestaurantFeedJsonParser.getSampleRestaurantList().restaurants
        dao.insertRestaurantFeed(restaurants)
    }

    override fun getSortedRestaurants(filter: String): Result<Flow<List<RestaurantFeed>>> {
        val query = if (filter == "status") {
            SimpleSQLiteQuery(
                "SELECT * FROM restaurantFeed ORDER BY " +
                        "CASE WHEN favorite THEN status = 'open' END DESC, " +
                        "CASE WHEN favorite THEN status = 'order ahead' END DESC, " +
                        "CASE WHEN favorite THEN status = 'closed' END DESC, " +
                        "CASE WHEN favorite=0 THEN status = 'open' END DESC, " +
                        "CASE WHEN favorite=0 THEN status = 'order ahead' END DESC, " +
                        "CASE WHEN favorite=0 THEN status = 'closed' END DESC"
            )
        } else {
            SimpleSQLiteQuery(
                "SELECT * FROM restaurantFeed ORDER BY " +
                        "CASE WHEN favorite THEN $filter END DESC, " +
                        "CASE WHEN favorite=0 THEN $filter END DESC"
            )
        }
        return try {
            val list = dao.getRestaurantFeed(query)
            Result.Success(list)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getRestaurantDetails(id: Int): Flow<Result<RestaurantFeed>> = flow {
        try {
            emit(Result.Success(dao.getRestaurantDetails(id)))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }.flowOn(provideIoDispatcher)

    override suspend fun setFavorite(restaurantFeed: RestaurantFeed) =
        withContext(provideIoDispatcher) {
            try {
                Result.Success(dao.updateRestaurant(restaurantFeed))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}