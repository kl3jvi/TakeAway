package com.example.takeaway.ui.restaurantdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.takeaway.databinding.FragmentRestaurantDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantDetailsFragment : Fragment() {

    private val args: RestaurantDetailsFragmentArgs by navArgs()
    private val restaurantDetailsViewModel: RestaurantDetailsViewModel by viewModels()
    private lateinit var view: FragmentRestaurantDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentRestaurantDetailsBinding.inflate(inflater, container, false)
        restaurantDetailsViewModel.restaurantDetails(args.restaurantName)
        initObservers()

        return view.root
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    restaurantDetailsViewModel.restaurantDetails.collect {
                        if (it == null) return@collect
                        view.restaurantDetails = it
                        view.apply {
                            restaurantDetails = it
                            executePendingBindings()
                        }
                    }
                }
            }
        }
    }
}