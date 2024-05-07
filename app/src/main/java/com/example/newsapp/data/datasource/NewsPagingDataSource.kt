package com.example.newsapp.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.remote.NewsAPI
import javax.inject.Inject

class NewsPagingDataSource @Inject constructor(private val api: NewsAPI,private var query: String) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val response = api.getNewsList(query = query, page = page, pageSize = params.loadSize)
            val articles = response.articles

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e("NewsPagingDataSource", "Error loading news: ${exception.message}")
            LoadResult.Error(exception)
        }
    }
}