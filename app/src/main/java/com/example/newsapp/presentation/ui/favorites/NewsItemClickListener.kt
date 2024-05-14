package com.example.newsapp.presentation.ui.favorites

import com.example.newsapp.data.local.NewsEntity

interface NewsItemClickListener {

    fun onNewsItemClicked(newsEntity: NewsEntity)

}