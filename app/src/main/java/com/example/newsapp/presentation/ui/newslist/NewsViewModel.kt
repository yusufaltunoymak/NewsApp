package com.example.newsapp.presentation.ui.newslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    val currentQuery: MutableStateFlow<String?> = MutableStateFlow("Android")
    private var searchJob: Job? = null

    val newsList: Flow<PagingData<Article>> = currentQuery.flatMapLatest { query ->
        newsRepository.getNewsList(query ?: "")
    }.flowOn(Dispatchers.IO).cachedIn(viewModelScope)

    fun setQuery(query: String) {
        currentQuery.value = query
    }

    fun searchWithDelay(query: String) {
        searchJob?.cancel()
        if (query.isNotEmpty()) {
            searchJob = viewModelScope.launch {
                delay(500)
                setQuery(query)
            }
        } else {
            setQuery("Android")
        }
    }

}