package com.example.takeaway.ui.restaurantdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takeaway.data.model.RestaurantFeed
import com.example.takeaway.data.restaurant.RestaurantDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(private val repository: RestaurantDetailsRepository) :
    ViewModel() {
    private val _restaurantDetails = MutableStateFlow<RestaurantFeed?>(null)
    private val _error = MutableStateFlow<String?>(null)

    val restaurantDetails: StateFlow<RestaurantFeed?>
        get() = _restaurantDetails

    fun restaurantDetails(restaurantName: String) = viewModelScope.launch {
        repository.getRestaurantDetails(restaurantName = restaurantName).collect { result ->
            _restaurantDetails.value = result.data
        }
    }
}