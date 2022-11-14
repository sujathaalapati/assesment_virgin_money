package com.sujata.virginmoneydemo

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@SuppressLint("CheckResult")
fun ImageView.loadImageFromUrl(url: String?, requestOptions: RequestOptions?=null) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.no_image).apply {
            requestOptions?.let {
                this.apply(requestOptions)
            }
        }
        .into(this)
}