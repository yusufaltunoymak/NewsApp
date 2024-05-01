package com.example.newsapp.presentation.ui.newslist

import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.NewsResponse

data class NewsListViewState(
    val isLoading : Boolean? = null,
    val newsList : List<Article>? = null
)