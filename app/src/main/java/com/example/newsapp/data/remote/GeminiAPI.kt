package com.example.newsapp.data.remote

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.model.SummaryResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GeminiAPI {
    @Headers("Authorization: Bearer" + BuildConfig.GEMINI_API_KEY)
    @GET("v1/summary")
    suspend fun getSummary(@Query("url") url: String): SummaryResponse
}