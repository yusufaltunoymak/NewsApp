package com.example.newsapp.presentation.ui.newslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.response.ResponseStatus
import com.example.newsapp.domain.usecase.GetNewsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsListUseCase: GetNewsListUseCase) : ViewModel() {

    private var _viewState = MutableStateFlow(NewsListViewState())
    val viewState: StateFlow<NewsListViewState> = _viewState

    init {
        getNewsList()
    }

    fun getNewsList(query: String = "turkey", page: Int = 1) {
        viewModelScope.launch {
            getNewsListUseCase.invoke(query, page).collect { response ->
                when (response.status) {
                    ResponseStatus.LOADING -> {
                        // Yükleme durumu, işlenebilir, ancak işlem yapılmayacak.
                    }
                    ResponseStatus.SUCCESS -> {
                        response.data?.let { newsResponse ->
                            // NewsResponse'dan Article listesine dönüştürme
                            val articles = newsResponse.articles ?: emptyList()
                            val articleList = articles.filterNotNull()
                            // ViewState güncelleme
                            _viewState.value = _viewState.value.copy(
                                isLoading = false,
                                newsList = articleList
                            )
                        }
                    }
                    else -> {
                        // Diğer durumlar için gerekirse işlem yapılabilir.
                    }
                }
            }
        }
    }

}