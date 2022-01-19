package com.example.takeaway.ui.restaurantfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takeaway.data.model.RestaurantFeed
import com.example.takeaway.data.restaurant.RestaurantFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantFeedViewModel @Inject constructor(private val repository: RestaurantFeedRepository) :
    ViewModel() {
    private val _restaurantFeedList = MutableStateFlow<List<RestaurantFeed>>(emptyList())
    private val _error = MutableStateFlow<String?>(null)

    val restaurantFeedList: StateFlow<List<RestaurantFeed>>
        get() = _restaurantFeedList
    val error: StateFlow<String?>
        get() = _error

    init {
        viewModelScope.launch {
            sortByBestMatch()
        }
    }

    fun setFavorite(restaurantFeed: RestaurantFeed) {
        viewModelScope.launch {
            restaurantFeed.favorite.not().also { restaurantFeed.favorite = it }
            repository.setFavorite(restaurantFeed)
        }
    }

    fun sortByMinCost() {
        viewModelScope.launch {
            repository.sortByMinCost().collect {
                _restaurantFeedList.value = it
            }
        }
    }

    fun sortByBestMatch() {
        viewModelScope.launch {
            repository.sortByBestMatch().collect {
                if (it.isNotEmpty()) {
                    _restaurantFeedList.value = it
                } else {
                    repository.fetchRestaurantFeed()
                }
            }
        }
    }

    fun sortByStatus() {
        _restaurantFeedList.value = repository.sortByStatus()
    }

    fun onErrorShown() {
        _error.value = null
    }
}