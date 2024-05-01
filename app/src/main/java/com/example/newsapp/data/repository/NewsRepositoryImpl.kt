package com.example.newsapp.data.repository

import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.remote.NewsAPI
import com.example.newsapp.data.response.Response
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.util.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val api : NewsAPI) : NewsRepository {
    override suspend fun getNewsList(
        query : String,
        page : Int
    ): Flow<Response<NewsResponse>> {
        return callbackFlow {
            val response =api.getNewsList(query,page,Constants.API_KEY)
            trySend(Response.Loading)
            response.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: retrofit2.Response<NewsResponse>
                ) {
                    if(response.isSuccessful) {
                        val body =response.body()
                        body?.let {
                            trySend(Response.Success(_data = it ))
                        }
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    trySend(Response.Error(t.message.toString()))
                }

            })
            awaitClose()
        }
    }
}
