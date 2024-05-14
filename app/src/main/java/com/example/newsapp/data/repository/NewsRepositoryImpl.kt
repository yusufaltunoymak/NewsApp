package com.example.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.datasource.NewsPagingDataSource
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.local.NewsEntity
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.remote.NewsAPI
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api : NewsAPI, private val newsDao : NewsDao) : NewsRepository {
    override suspend fun getNewsList(query : String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = {NewsPagingDataSource(api,query)}
        ).flow
    }

    override suspend fun insertNews(newsEntity: NewsEntity) {
        newsDao.insertNews(newsEntity)
    }

    override suspend fun deleteNewsFromDatabase(newsEntity: NewsEntity) {
        newsDao.delete(newsEntity)
    }

    override suspend fun getAllNewsList(): Flow<List<NewsEntity>> = newsDao.getNewsList()
    override suspend fun isFavoriteNews(url: String): Boolean {
        return newsDao.getArticleByUrl(url) != null
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 3
    }

}
