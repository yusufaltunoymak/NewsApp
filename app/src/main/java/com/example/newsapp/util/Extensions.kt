package com.example.newsapp.util

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsapp.R

fun ImageView.downloadFromUrl(url: String?, context: Context,placeholderResourceId : Int) {
    val options = RequestOptions()
        .placeholder(placeholderResourceId)
        .error(ContextCompat.getDrawable(context, R.color.light_grey))

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .dontAnimate()
        .fitCenter().into(this)
}

fun cleanText(text: String?): String {
    return text?.replace(Regex("<[^>]*>|&[^;]+;|\\r\\n|\\t"), "")?.replace(Regex("[^A-Za-z0-9\\s]+"), "") ?: ""
}