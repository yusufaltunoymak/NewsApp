package com.example.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.datasource.NewsPagingDataSource
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.remote.NewsAPI
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api : NewsAPI) : NewsRepository {
    override suspend fun getNewsList(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = {NewsPagingDataSource(api)}
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 3
    }

}
