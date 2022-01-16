package com.example.takeaway.ui.restaurantfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takeaway.data.api.Result
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
    private val _restaurantList = MutableStateFlow<List<RestaurantFeed>>(emptyList())
    private val _error = MutableStateFlow<String?>(null)

    val restaurantList: StateFlow<List<RestaurantFeed>>
        get() = _restaurantList
    val error: StateFlow<String?>
        get() = _error

    init {
        viewModelScope.launch {
            repository.getRestaurantFeedList().collect { result ->
                if (result is Result.Success) {
                    _restaurantList.value = result.data
                } else if (result is Result.Error) {
                    result.exception.message?.let {
                        _error.value = it
                    }
                }
            }
        }
    }

    fun onErrorShown() {
        _error.value = null
    }
}