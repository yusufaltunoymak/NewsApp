package com.example.newsapp.presentation.ui.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.local.NewsEntity
import com.example.newsapp.databinding.ListRecyclerItemBinding
import com.example.newsapp.util.cleanText
import com.example.newsapp.util.downloadFromUrl

class FavoritesAdapter() : ListAdapter<NewsEntity,FavoritesAdapter.ViewHolder>(FavoritesCallBack()) {

    inner class ViewHolder(private val binding : ListRecyclerItemBinding, private val context : Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(newsEntity: NewsEntity?) {
            newsEntity?.let {
                binding.apply {
                    if (!newsEntity.urlToImage.isNullOrEmpty()) {
                        newsPosterImage.downloadFromUrl(newsEntity.urlToImage, context, R.drawable.news_logo)
                        Glide.with(itemView.context).load(newsEntity.urlToImage).into(newsPosterImage)
                    }
                    else {
                        newsPosterImage.setImageResource(R.drawable.news_logo)

                    }
                    newsHeaderText.text = newsEntity.title
                    newsContentText.text = cleanText(newsEntity.content)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding,parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class FavoritesCallBack : DiffUtil.ItemCallback<NewsEntity>() {
    override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
        return oldItem == newItem

    }

    override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
        return oldItem == newItem
    }

}