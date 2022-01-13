package com.example.takeaway.ui.restaurantfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.data.model.RestaurantFeed
import com.example.takeaway.databinding.FragmentRestaurantFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantFeedFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRestaurantFeedBinding.inflate(inflater, container, false)
        binding.restaurantFeedRecycler
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = listOf(
            RestaurantFeed(1, "foo", "bar"),
            RestaurantFeed(2, "foo", "descr"),
            RestaurantFeed(3, "bar", "descr")
        )
        showRestaurantFeed(binding.restaurantFeedRecycler, list)
    }

    private fun showRestaurantFeed(recyclerView: RecyclerView, list: List<RestaurantFeed>) {
        if (recyclerView.adapter == null) {
            recyclerView.adapter = RestaurantFeedAdapter()
        }
        (recyclerView.adapter as RestaurantFeedAdapter).submitList(list)
    }
}