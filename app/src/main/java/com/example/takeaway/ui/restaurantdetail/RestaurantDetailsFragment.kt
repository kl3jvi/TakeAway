package com.example.takeaway.ui.restaurantdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.takeaway.R
import com.example.takeaway.databinding.FragmentRestaurantDetailsBinding
import com.google.android.material.snackbar.Snackbar
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
        view.restaurantDetailsToolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
        restaurantDetailsViewModel.restaurantDetails(args.restaurantId)
        initObservers()

        return view.root
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    restaurantDetailsViewModel.restaurantDetails.collect { result ->
                        if (result == null) return@collect
                        view.restaurantDetails = result
                        view.apply {
                            restaurantDetails = result
                            executePendingBindings()
                        }

                        view.restaurantDetailsFavorite.setOnClickListener {
                            restaurantDetailsViewModel.setFavorite(result)
                            if (result.favorite) {
                                view.restaurantDetailsFavorite.setImageResource(R.drawable.outline_favorite_24)
                            } else {
                                view.restaurantDetailsFavorite.setImageResource(R.drawable.outline_favorite_border_24)
                            }
                        }
                    }
                }
                launch {
                    restaurantDetailsViewModel.isFavorite.collect {
                        it?.let {
                            val message: String =
                                if (it) getString(R.string.add_favorite) else getString(R.string.remove_favorite)
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            restaurantDetailsViewModel.onToastShown()
                        }
                    }
                }
                launch {
                    restaurantDetailsViewModel.error.collect {
                        it?.let {
                            Snackbar.make(view.root, it, Snackbar.LENGTH_LONG).show()
                            restaurantDetailsViewModel.onErrorShown()
                        }
                    }
                }
            }
        }
    }
}