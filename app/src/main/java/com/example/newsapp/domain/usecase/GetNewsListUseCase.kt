package com.example.newsapp.domain.usecase

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.data.response.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsListUseCase @Inject constructor(private val newsRepositoryImpl: NewsRepositoryImpl) {
    suspend operator fun invoke(query: String, page: Int): Flow<Response<NewsResponse>> =
        newsRepositoryImpl.getNewsList(query, page)
}