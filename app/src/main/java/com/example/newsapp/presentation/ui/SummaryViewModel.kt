package com.example.newsapp.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.SummaryData
import com.example.newsapp.data.model.SummaryResponse
import kotlinx.coroutines.launch

class SummaryViewModel : ViewModel() {
    val summaryResponse: MutableLiveData<SummaryResponse> = MutableLiveData()

    fun getSummary(url: String) {
        viewModelScope.launch {
            val response = SummaryData.getResponse(url)
            summaryResponse.value = response
        }
    }
}