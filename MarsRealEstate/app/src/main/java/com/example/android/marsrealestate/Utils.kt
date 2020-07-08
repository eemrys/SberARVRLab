package com.example.android.marsrealestate

import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}