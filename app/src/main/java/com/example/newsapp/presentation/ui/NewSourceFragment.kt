package com.example.newsapp.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.BottomSheetLayoutBinding
import com.example.newsapp.databinding.FragmentNewSourceBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class NewSourceFragment : BaseFragment<FragmentNewSourceBinding>(FragmentNewSourceBinding::inflate) {
    private val summaryViewModel : SummaryViewModel by viewModels()
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
            summaryViewModel.summaryResponse.observe(viewLifecycleOwner) { response ->
                val summaryText = response.summary.removePrefix("SummaryResponse(summary = ").removeSuffix(")")
                val bottomSheetDialog = BottomSheetDialog(requireContext())
                val bottomSheetBinding = BottomSheetLayoutBinding.inflate(layoutInflater)
                bottomSheetBinding.summaryTextView.text = summaryText
                bottomSheetDialog.setContentView(bottomSheetBinding.root)
                bottomSheetDialog.show()
            }
            summarizeIv.setOnClickListener {
                summarize(url)
            }
        }
    }
    private fun summarize(url : String) {
        viewLifecycleOwner.lifecycleScope.launch {
            summaryViewModel.getSummary(url + getString(R.string.prompt_text))
        }
    }
}