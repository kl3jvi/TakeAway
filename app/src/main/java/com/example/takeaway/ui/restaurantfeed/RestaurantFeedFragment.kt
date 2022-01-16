package com.example.takeaway.ui.restaurantfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.data.model.RestaurantFeed
import com.example.takeaway.databinding.FragmentRestaurantFeedBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RestaurantFeedFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantFeedBinding
    private val restaurantFeedViewModel: RestaurantFeedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantFeedBinding.inflate(inflater, container, false)
        binding.restaurantFeedRecycler

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    restaurantFeedViewModel.restaurantList.collect {
                        showRestaurantFeed(binding.restaurantFeedRecycler, it)
                    }
                }

                launch {
                    restaurantFeedViewModel.error.collect {
                        it?.let {
                            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                            restaurantFeedViewModel.onErrorShown()
                        }
                    }
                }
            }
        }
    }

    private fun showRestaurantFeed(recyclerView: RecyclerView, list: List<RestaurantFeed>) {
        if (recyclerView.adapter == null) {
            recyclerView.adapter = RestaurantFeedAdapter()
        }
        (recyclerView.adapter as RestaurantFeedAdapter).submitList(list)
    }
}