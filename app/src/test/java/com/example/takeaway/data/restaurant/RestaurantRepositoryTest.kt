package com.example.takeaway.data.restaurant

import com.example.takeaway.data.api.Result
import com.example.takeaway.data.db.RestaurantDao
import com.example.takeaway.util.MainCoroutineRule
import com.example.takeaway.util.TestData
import com.example.takeaway.util.runBlockingTest
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantRepositoryTest {
    private lateinit var dao: RestaurantDao
    private lateinit var repository: RestaurantRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        dao = mock(RestaurantDao::class.java)
        repository = RestaurantRepositoryImpl(dao, Dispatchers.Main)
    }

    @Test
    fun getSortedRestaurants_sortByStatus() = mainCoroutineRule.runBlockingTest {
        //TODO couldn't mock dao.getRestaurantFeed(query), I need to find another solution

//        val query = TestData.sortByFilterQuery("foo")
//        val resList = flowOf(TestData.restaurantList)
//        `when`(dao.getRestaurantFeed(query)).thenReturn(
//            resList
//        )
//        val list = repository.getSortedRestaurants("foo")
//        if (list is Result.Success) {
//            Truth.assertThat(list.data).isEqualTo(Result.Success(TestData.restaurant1))
//        }
    }

    @Test
    fun getRestaurantDetails_success() = mainCoroutineRule.runBlockingTest {
        `when`(dao.getRestaurantDetails(1)).thenReturn(TestData.restaurant1)
        Truth.assertThat(repository.getRestaurantDetails(1).first())
            .isEqualTo(Result.Success(TestData.restaurant1))
    }

    @Test
    fun getRestaurantDetails_error() = mainCoroutineRule.runBlockingTest {
        `when`(dao.getRestaurantDetails(1)).thenThrow(RuntimeException("No data"))
        val restaurant = repository.getRestaurantDetails(1).first()
        Truth.assertThat(restaurant is Result.Error).isTrue()
        if (restaurant is Result.Error) {
            Truth.assertThat(restaurant.exception.message).isEqualTo("No data")
        }
    }

    @Test
    fun setFavorite_success() = mainCoroutineRule.runBlockingTest {
        `when`(dao.updateRestaurant(TestData.restaurant1)).thenReturn(1)
        Truth.assertThat(repository.setFavorite(TestData.restaurant1)).isEqualTo(
            Result.Success(1)
        )
    }

    @Test
    fun setFavorite_Error() = mainCoroutineRule.runBlockingTest {
        `when`(dao.updateRestaurant(TestData.restaurant1)).thenThrow(RuntimeException("No data"))

        val list = repository.setFavorite(TestData.restaurant1)
        if (list is Result.Error) {
            Truth.assertThat(list.exception.message).isEqualTo(
                "No data"
            )
        }
    }
}