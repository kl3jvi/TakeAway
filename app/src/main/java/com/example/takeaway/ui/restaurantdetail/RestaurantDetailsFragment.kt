package com.example.takeaway.ui.restaurantdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.takeaway.databinding.FragmentRestaurantDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailsFragment : Fragment() {

    private val args: RestaurantDetailsFragmentArgs  by navArgs()

    private lateinit var view: FragmentRestaurantDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentRestaurantDetailsBinding.inflate(inflater, container, false)

        return view.root
    }
}