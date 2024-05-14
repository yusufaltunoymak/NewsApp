package com.example.newsapp.presentation.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentNewSourceBinding


class NewSourceFragment : BaseFragment<FragmentNewSourceBinding>(FragmentNewSourceBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url") ?: ""
        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            binding.webView.loadUrl(url)
        }
        binding.apply {
            urlTextView.text = url
            closeIv.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}