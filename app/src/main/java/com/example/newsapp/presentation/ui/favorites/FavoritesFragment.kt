package com.example.newsapp.presentation.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.base.BaseFragment
import com.example.newsapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {
    private lateinit var favoritesAdapter : FavoritesAdapter
    private val newsFavoritesViewModel : FavoritesViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        favoritesAdapter = FavoritesAdapter()
        binding.favoritesRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoritesAdapter
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            newsFavoritesViewModel.newsFavoritesItems.collect {newsList ->
                favoritesAdapter.submitList(newsList)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        newsFavoritesViewModel.getNewsList()
    }

}