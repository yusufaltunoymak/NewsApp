package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.response.Response
import kotlinx.coroutines.flow.Flow

    interface NewsRepository {
        suspend fun getNewsList() :Flow<PagingData<Article>>
    }