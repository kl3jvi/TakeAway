package com.example.takeaway.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.takeaway.R

@BindingAdapter("bindFavorite")
fun bindFavoriteIcon(view: ImageView, isFavorite: Boolean) {
    if (isFavorite) {
        view.setImageResource(R.drawable.outline_favorite_24)
    } else {
        view.setImageResource(R.drawable.outline_favorite_border_24)
    }
}