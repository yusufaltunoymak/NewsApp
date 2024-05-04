package com.example.newsapp.presentation.ui.newslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.FooterLayoutRecyclerItemBinding

class NewsListLoadStateAdapter(private val retry : () -> Unit) : LoadStateAdapter<NewsListLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: FooterLayoutRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.footerRetryButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                footerProgressBar.isVisible = loadState is LoadState.Loading
                footerRetryButton.isVisible = loadState !is LoadState.Loading
                footerTextView.isVisible = loadState !is LoadState.Loading
            }
        }
        }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState = loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = FooterLayoutRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return LoadStateViewHolder(binding)
    }
}
