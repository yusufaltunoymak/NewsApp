package com.example.newsapp.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.newsapp.data.repository.ConnectivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ConnectivityViewModel @Inject constructor(connectivityRepository: ConnectivityRepository) : ViewModel(){
    val isConnected = connectivityRepository.isConnected.asLiveData()
}