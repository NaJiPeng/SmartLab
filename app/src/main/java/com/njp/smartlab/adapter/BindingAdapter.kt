package com.njp.smartlab.adapter

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("url")
fun ImageView.setUrl(url: String) {
    Glide.with(this)
            .load(url)
            .into(this)
}