package com.example.takeaway.ui.restaurantdetail

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
class RestaurantDetailsViewModel @Inject constructor(private val repository: RestaurantRepository) :
    ViewModel() {
    private val _restaurantDetails = MutableStateFlow<RestaurantFeed?>(null)
    private val _error = MutableStateFlow<String?>(null)
    private val _isFavorite = MutableStateFlow<Boolean?>(null)

    val restaurantDetails: StateFlow<RestaurantFeed?>
        get() = _restaurantDetails
    val error: StateFlow<String?>
        get() = _error
    val isFavorite: StateFlow<Boolean?>
        get() = _isFavorite

    fun restaurantDetails(restaurantId: Int) = viewModelScope.launch {
        repository.getRestaurantDetails(id = restaurantId).collect { result ->
            when (result) {
                is Result.Success -> {
                    _restaurantDetails.value = result.data
                }
                is Result.Error -> {
                    _error.value = result.exception.message
                }
            }
        }
    }

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

    fun onErrorShown() {
        _error.value = null
    }

    fun onToastShown() {
        _isFavorite.value = null
    }
}