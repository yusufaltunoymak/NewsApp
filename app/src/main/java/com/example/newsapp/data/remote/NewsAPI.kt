package com.example.newsapp.data.remote

import com.example.newsapp.data.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("everything")
    fun getNewsList(
        @Query("q") query : String,
        @Query("page") page : Int,
        @Query("apiKey") apiKey : String
    ) : Call<NewsResponse>
}