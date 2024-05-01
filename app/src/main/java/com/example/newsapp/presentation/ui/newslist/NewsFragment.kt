package com.example.newsapp.presentation.ui.newslist

import NewsListAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {
    private val newsViewModel : NewsViewModel by viewModels()
    private val adapter : NewsListAdapter = NewsListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        newsViewModel.getNewsList()
        observeData()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.viewState.collect {viewState ->
                viewState.apply {
                    binding.apply {
                        newsList?.let {newsList ->
                            adapter.submitList(newsList)
                            setupMostListenedRecycler()
                        }
                    }
                }
            }
        }
    }

    private fun setupMostListenedRecycler(){
        val mostListenedRecycler = binding.newsRv
        mostListenedRecycler.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        mostListenedRecycler.adapter = adapter

    }
}