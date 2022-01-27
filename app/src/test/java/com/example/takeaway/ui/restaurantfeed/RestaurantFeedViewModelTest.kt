package com.example.takeaway.ui.restaurantfeed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.takeaway.ui.FakeRestaurantRepository
import com.example.takeaway.util.MainCoroutineRule
import com.example.takeaway.util.TestData
import com.example.takeaway.util.runBlockingTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantFeedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var repository: FakeRestaurantRepository
    private lateinit var viewModel: RestaurantFeedViewModel

    @Before
    fun setup() {
        repository = FakeRestaurantRepository()
        viewModel = RestaurantFeedViewModel(repository)
    }

    @Test
    fun getSortedRestaurants_success_returnFeedList() = coroutineRule.runBlockingTest {
        viewModel.getSortedRestaurants("foo")
        assertThat(viewModel.restaurantFeedList.first()).isEqualTo(TestData.restaurantList)
    }

    @Test
    fun getSortedRestaurants_error_returnException() = coroutineRule.runBlockingTest {
        repository.setReturnError(true)
        viewModel.getSortedRestaurants("foo")
        assertThat(viewModel.error.first()).isEqualTo("No data")
    }

    @Test
    fun setFavorite_success() {
        repository.setReturnError(false)
        viewModel.setFavorite(TestData.restaurant1)
        assertThat(viewModel.isFavorite.value).isTrue()
    }

    @Test
    fun removeFavorite_success() {
        repository.setReturnError(false)
        viewModel.setFavorite(TestData.restaurant4)
        assertThat(viewModel.isFavorite.value).isFalse()
    }

    @Test
    fun setFavorite_error() {
        repository.setReturnError(true)
        viewModel.setFavorite(TestData.restaurant1)
        assertThat(viewModel.error.value).isEqualTo("No data")
    }
}
