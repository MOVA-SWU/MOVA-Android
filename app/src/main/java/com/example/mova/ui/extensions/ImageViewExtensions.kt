package com.example.mova.ui.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.mova.R

fun ImageView.load(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this)
            .load(url)
            .placeholder(R.color.gray_55)
            .error(R.color.gray_55)
            .into(this)
    } else {
        setBackgroundResource(R.color.gray_55)
    }
}