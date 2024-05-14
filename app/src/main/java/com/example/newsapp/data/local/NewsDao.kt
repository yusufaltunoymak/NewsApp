package com.example.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity)

    @Query("SELECT * FROM news_table")
    fun getNewsList() : Flow<List<NewsEntity>>

    @Delete
    suspend fun delete(newsEntity: NewsEntity)

    @Query("SELECT * FROM news_table WHERE url = :url")
    suspend fun getArticleByUrl(url: String): NewsEntity?
}