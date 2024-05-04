package com.example.newsapp.presentation.ui.newslist

import NewsListAdapter
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter : NewsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNewsList()
        initAdapter()
        setupMostListenedRecycler()
    }
    private fun observeNewsList() {
        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.newsList.collect { pagingData ->
               newsAdapter.submitData(pagingData)
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsListAdapter(requireContext())
        newsAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                newsRv.isVisible = loadState.source.refresh is LoadState.NotLoading
                retryButton.isVisible = loadState.source.refresh is LoadState.Error
                errorText.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.Error) {
                    errorText.text =
                        (loadState.source.refresh as LoadState.Error).error.localizedMessage
                }
            }
        }

    }
    private fun setupMostListenedRecycler() {
        binding.newsRv.apply {
            binding.newsRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.adapter = newsAdapter.withLoadStateFooter(footer = NewsListLoadStateAdapter {newsAdapter.retry()})
        }

    }
}
