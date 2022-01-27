package com.example.takeaway.ui.restaurantfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.takeaway.R
import com.example.takeaway.data.model.RestaurantFeed
import com.example.takeaway.databinding.ListItemRestaurantBinding

class RestaurantFeedAdapter(private val viewModel: RestaurantFeedViewModel) :
    ListAdapter<RestaurantFeed, RestaurantFeedAdapter.RestaurantFeedViewHolder>(PlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantFeedViewHolder {
        return RestaurantFeedViewHolder(
            ListItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RestaurantFeedViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.apply {
            bind(onItemClickListener(restaurant.id), restaurant, viewModel)
        }
    }

    class RestaurantFeedViewHolder(
        private val binding: ListItemRestaurantBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            listener: View.OnClickListener,
            item: RestaurantFeed,
            viewModel: RestaurantFeedViewModel
        ) {
            binding.apply {
                onItemClickListener = listener
                restaurant = item
                executePendingBindings()
            }

            binding.restaurantFavorite.setOnClickListener {
                viewModel.setFavorite(item)
                if (item.favorite) {
                    binding.restaurantFavorite.setImageResource(R.drawable.outline_favorite_24)
                } else {
                    binding.restaurantFavorite.setImageResource(R.drawable.outline_favorite_border_24)
                }
            }
        }
    }

    private fun onItemClickListener(restaurantId: Int): View.OnClickListener {
        return View.OnClickListener {
            val direction =
                RestaurantFeedFragmentDirections.actionRestaurantFeedFragmentToRestaurantDetailsFragment(
                    restaurantId = restaurantId
                )
            it.findNavController().navigate(direction)
        }
    }
}

private class PlantDiffCallback : DiffUtil.ItemCallback<RestaurantFeed>() {

    override fun areItemsTheSame(oldItem: RestaurantFeed, newItem: RestaurantFeed): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RestaurantFeed, newItem: RestaurantFeed): Boolean {
        return oldItem == newItem
    }
}