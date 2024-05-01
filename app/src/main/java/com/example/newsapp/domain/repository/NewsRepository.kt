package com.example.newsapp.domain.repository

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.response.Response
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsList(query : String, page : Int) :Flow<Response<NewsResponse>>
}