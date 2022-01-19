package com.example.takeaway.ui.restaurantfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.R
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
        initSortAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    restaurantFeedViewModel.restaurantFeedList.collect {
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
            recyclerView.adapter = RestaurantFeedAdapter(restaurantFeedViewModel)
        }
        (recyclerView.adapter as RestaurantFeedAdapter).submitList(list)
    }

    private fun initSortAdapter() {
        val spinner = binding.feedSortSpinner

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sort_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = it
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> {
                        restaurantFeedViewModel.sortByBestMatch()
                    }
                    7 -> {
                        restaurantFeedViewModel.sortByMinCost()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }
}