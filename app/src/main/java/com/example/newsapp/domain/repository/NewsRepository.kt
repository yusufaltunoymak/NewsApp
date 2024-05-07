package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsList(query : String): Flow<PagingData<Article>>
}