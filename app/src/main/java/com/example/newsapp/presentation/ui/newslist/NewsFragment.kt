package com.example.newsapp.presentation.ui.newslist

import NewsListAdapter
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.presentation.ui.ConnectivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {
    private val newsViewModel: NewsViewModel by viewModels()
    private val connectivityViewModel: ConnectivityViewModel by viewModels()

    private lateinit var newsAdapter: NewsListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNewsList()
        initAdapter()
        setupNewsRecycler()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { query ->
                    if (query.length % 2 == 0) {
                        search(query)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { newText ->
                    if (newText.length % 2 == 0) {
                        search(newText)
                    }
                }
                return true
            }
        })
    }

    private fun observeNewsList() {
        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.newsList.collect { pagingData ->
                newsAdapter.submitData(pagingData)
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsListAdapter(requireContext()) { article ->
            findNavController().navigate(
                NewsFragmentDirections.actionNewsFragmentToNewDetailFragment(
                    article
                )
            )
        }
        newsAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                newsRv.isVisible = loadState.source.refresh is LoadState.NotLoading
                retryButton.isVisible = loadState.source.refresh is LoadState.Error
                errorText.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.Error) {
                    connectivityViewModel.isConnected.observe(viewLifecycleOwner) { isConnected ->
                        if (!isConnected) {
                            binding.errorText.text = getString(R.string.no_internet_connection_text)
                            errorText.visibility = View.VISIBLE
                        } else {
                            errorText.text =
                                (loadState.source.refresh as LoadState.Error).error.localizedMessage
                        }

                    }
                }
            }
        }
    }

    private fun setupNewsRecycler() {
        binding.newsRv.apply {
            binding.newsRv.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            this.adapter =
                newsAdapter.withLoadStateFooter(footer = NewsListLoadStateAdapter { newsAdapter.retry() })
        }

    }

    fun search(searchWord: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.searchWithDelay(searchWord)
        }
    }

    override fun onResume() {
        super.onResume()
        observeNewsList()
        binding.apply {
            searchView.setQuery(newsViewModel.currentQuery.value, false)
            searchView.queryHint = getString(R.string.something_search_news_text)
        }
    }
}
