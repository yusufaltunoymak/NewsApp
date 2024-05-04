package com.example.newsapp.data.remote

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("everything")
    suspend fun getNewsList(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("pageSize") pageSize: Int
    ): NewsResponse
}
