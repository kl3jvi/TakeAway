package com.example.takeaway.ui.restaurantfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takeaway.data.api.Result
import com.example.takeaway.data.model.RestaurantFeed
import com.example.takeaway.data.restaurant.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantFeedViewModel @Inject constructor(
    private val repository: RestaurantRepository
) :
    ViewModel() {
    private val _restaurantFeedList = MutableStateFlow<List<RestaurantFeed>>(emptyList())
    private val _error = MutableStateFlow<String?>(null)
    private val _isFavorite = MutableStateFlow<Boolean?>(null)

    val restaurantFeedList: StateFlow<List<RestaurantFeed>>
        get() = _restaurantFeedList
    val error: StateFlow<String?>
        get() = _error
    val isFavorite: StateFlow<Boolean?>
        get() = _isFavorite


    fun setFavorite(restaurant: RestaurantFeed) {
        viewModelScope.launch {
            restaurant.favorite.not().also { restaurant.favorite = it }
            repository.setFavorite(restaurant).let { result ->
                if (result is Result.Success) {
                    _isFavorite.value = restaurant.favorite
                } else if (result is Result.Error) {
                    _error.value = result.exception.message
                }
            }
        }
    }


    fun getSortedRestaurants(filter: String) {
        viewModelScope.launch {
            repository.getSortedRestaurants(filter).let { result ->
                if (result is Result.Success) {
                    result.data.collect {
                        if (it.isEmpty()) getRemoteRestaurantsFeed()
                        else _restaurantFeedList.value = it
                    }
                } else if (result is Result.Error) {
                    _error.value = result.exception.message
                }
            }
        }
    }

    private fun getRemoteRestaurantsFeed() {
        viewModelScope.launch {
            repository.fetchRestaurantFeed()
        }
    }

    fun onErrorShown() {
        _error.value = null
    }

    fun onToastShown() {
        _isFavorite.value = null
    }
}