package com.example.newsapp.presentation.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.NewsEntity
import com.example.newsapp.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    private val _newsFavoritesItems = MutableStateFlow<List<NewsEntity>>(emptyList())
    val newsFavoritesItems: StateFlow<List<NewsEntity>> = _newsFavoritesItems

    fun addFavorite(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.insertNews(news)
        }
    }

    fun getNewsList() {
        viewModelScope.launch {
            newsRepository.getAllNewsList().collect { newsList ->
                _newsFavoritesItems.value = newsList
            }
        }
    }

    fun deleteFavorite(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.deleteNewsFromDatabase(news)
        }
    }

    fun isFavoriteNews(news: NewsEntity): LiveData<Boolean> {
        val isFavorite = MutableLiveData<Boolean>()
        viewModelScope.launch {
            isFavorite.value =  newsRepository.isFavoriteNews(news.newsId)
        }
        return isFavorite
    }

}