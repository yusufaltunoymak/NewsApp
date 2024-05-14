package com.example.newsapp.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.Source

@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey
    var newsId: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @Embedded(prefix = "source_") val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)
