package com.example.newsapp.presentation.ui.newsdetail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentNewDetailBinding
import com.example.newsapp.util.Constants
import com.example.newsapp.util.cleanText
import com.example.newsapp.util.downloadFromUrl
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewDetailFragment : BaseFragment<FragmentNewDetailBinding>(FragmentNewDetailBinding::inflate) {
    private val args : NewDetailFragmentArgs by navArgs()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articles = args.articles
        binding.apply {
            textViewAuthor.text = articles.author
            textViewTitle.text = articles.title
            textViewContent.text = cleanText(articles.content)
            val publishedDate = articles.publishedAt
            textViewpublishedAt.text = formattedDate(publishedDate)

            if (!articles.urlToImage.isNullOrEmpty()) {
                newsImage.downloadFromUrl(articles.urlToImage, requireContext(), R.drawable.news_logo)
                Glide.with(newsImage.context).load(articles.urlToImage).into(newsImage)
            }
            else {
                newsImage.setImageResource(R.drawable.news_logo)

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formattedDate(date : String?) : String {
        val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val formatter = DateTimeFormatter.ofPattern(Constants.dateFormat)
        val formattedDate = dateTime.format(formatter)
        return formattedDate
    }
}