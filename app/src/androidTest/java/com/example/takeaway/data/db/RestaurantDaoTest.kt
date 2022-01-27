@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.takeaway.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.takeaway.util.TestData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var restaurantDao: RestaurantDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        restaurantDao = database.RestaurantDao()

        restaurantDao.insertRestaurantFeed(TestData.restaurantList)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetRestaurantFeed_sortByStatus() = runTest {
        val restaurantFeed = restaurantDao.getRestaurantFeed(TestData.sortByStatusQuery).first()
        assertThat(restaurantFeed.size).isEqualTo(5)

        assertThat(restaurantFeed[0]).isEqualTo(TestData.restaurant4)
        assertThat(restaurantFeed[1]).isEqualTo(TestData.restaurant2)
        assertThat(restaurantFeed[2]).isEqualTo(TestData.restaurant3)
        assertThat(restaurantFeed[3]).isEqualTo(TestData.restaurant1)
        assertThat(restaurantFeed[4]).isEqualTo(TestData.restaurant5)
    }

    @Test
    fun testGetRestaurantFeed_sortByFilter() = runTest {
        val restaurantFeed =
            restaurantDao.getRestaurantFeed(TestData.sortByFilterQuery("sorting_bestMatch")).first()
        assertThat(restaurantFeed.size).isEqualTo(5)

        assertThat(restaurantFeed[0]).isEqualTo(TestData.restaurant2)
        assertThat(restaurantFeed[1]).isEqualTo(TestData.restaurant3)
        assertThat(restaurantFeed[2]).isEqualTo(TestData.restaurant4)
        assertThat(restaurantFeed[3]).isEqualTo(TestData.restaurant5)
        assertThat(restaurantFeed[4]).isEqualTo(TestData.restaurant1)
    }

    @Test
    fun testGetRestaurantFeed_setFavorite_sortByFilter() = runTest {
        val restaurant = TestData.restaurant1.apply {
            favorite = true
        }
        restaurantDao.updateRestaurant(restaurant)

        val restaurantFeed =
            restaurantDao.getRestaurantFeed(TestData.sortByFilterQuery("sorting_distance")).first()
        assertThat(restaurantFeed.size).isEqualTo(5)

        assertThat(restaurantFeed[0]).isEqualTo(TestData.restaurant2)
        assertThat(restaurantFeed[1]).isEqualTo(TestData.restaurant3)
        assertThat(restaurantFeed[2]).isEqualTo(TestData.restaurant1)
        assertThat(restaurantFeed[3]).isEqualTo(TestData.restaurant4)
        assertThat(restaurantFeed[4]).isEqualTo(TestData.restaurant5)

        // revert favorite value to not break other tests
        restaurant.apply {
            favorite = false
        }
        restaurantDao.updateRestaurant(restaurant)
    }

    @Test
    fun testGetRestaurantDetails() = runTest {
        assertThat(
            restaurantDao.getRestaurantDetails(TestData.restaurant1.id).first()
        ).isEqualTo(TestData.restaurant1)
    }

}