package com.example.newsapp.util

import com.example.newsapp.data.local.NewsEntity
import com.example.newsapp.data.model.Article

fun NewsEntity.toArticle(): Article {
    return Article(
        source = this.source,
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}