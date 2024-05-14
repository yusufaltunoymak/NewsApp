package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.data.local.NewsEntity
import com.example.newsapp.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsList(query : String): Flow<PagingData<Article>>
    suspend fun insertNews(newsEntity: NewsEntity)
    suspend fun deleteNewsFromDatabase(newsEntity: NewsEntity)
    suspend fun getAllNewsList() : Flow<List<NewsEntity>>

    suspend fun isFavoriteNews(url : String): Boolean
}