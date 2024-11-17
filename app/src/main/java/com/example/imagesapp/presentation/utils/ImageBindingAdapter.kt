package com.example.imagesapp.presentation.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: AppCompatImageView, url: String?) {
    url?.let {
        Glide.with(view.context)
            .load(it)
            .centerCrop()
            .into(view)
    }
}