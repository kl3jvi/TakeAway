package com.example.takeaway.ui.restaurantdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.takeaway.ui.FakeRestaurantRepository
import com.example.takeaway.util.MainCoroutineRule
import com.example.takeaway.util.TestData
import com.example.takeaway.util.runBlockingTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private lateinit var repository: FakeRestaurantRepository
    private lateinit var viewModel: RestaurantDetailsViewModel

    @Before
    fun setup() {
        repository = FakeRestaurantRepository()
        viewModel = RestaurantDetailsViewModel(repository)
    }

    @Test
    fun getRestaurantDetails_success_returnRestaurantDetails() = coroutineRule.runBlockingTest {
        viewModel.restaurantDetails(1)
        Truth.assertThat(viewModel.restaurantDetails.first()).isEqualTo(TestData.restaurant1)
    }

    @Test
    fun getRestaurantDetails_error_returnException() = coroutineRule.runBlockingTest {
        repository.setReturnError(true)
        viewModel.restaurantDetails(1)
        Truth.assertThat(viewModel.error.first()).isEqualTo("No data")
    }


    @Test
    fun setFavorite_success() {
        repository.setReturnError(false)
        viewModel.setFavorite(TestData.restaurant1)
        Truth.assertThat(viewModel.isFavorite.value).isTrue()
    }

    @Test
    fun removeFavorite_success() {
        repository.setReturnError(false)
        viewModel.setFavorite(TestData.restaurant2)
        Truth.assertThat(viewModel.isFavorite.value).isFalse()
    }

    @Test
    fun setFavorite_error() {
        repository.setReturnError(true)
        viewModel.setFavorite(TestData.restaurant1)
        Truth.assertThat(viewModel.error.value).isEqualTo("No data")
    }
}