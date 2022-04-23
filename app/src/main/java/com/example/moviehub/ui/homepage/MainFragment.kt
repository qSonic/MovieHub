package com.example.moviehub.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviehub.adapters.FilmItemListener
import com.example.moviehub.adapters.MainFragmentListAdapter
import com.example.moviehub.data.model.Film
import com.example.moviehub.databinding.MainFragmentBinding
import com.example.moviehub.ui.core.BaseFragment
import com.example.moviehub.ui.core.bindVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>(), FilmItemListener {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var popularMainFragmentAdapter: MainFragmentListAdapter
    private lateinit var topMainFragmentAdapter: MainFragmentListAdapter
    private lateinit var awaitMainFragmentAdapter: MainFragmentListAdapter

    @OptIn(InternalCoroutinesApi::class)
    override fun onBindingCreated(binding: MainFragmentBinding, savedInstanceState: Bundle?) {
        super.onBindingCreated(binding, savedInstanceState)
        setupRecyclerViews(binding = binding)
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is MainFragmentUiState.Success -> {
                        topMainFragmentAdapter.setItems(state.items[0].data!!.films)
                        popularMainFragmentAdapter.setItems(state.items[2].data!!.films)
                        awaitMainFragmentAdapter.setItems(state.items[1].data!!.films)
                        changeViewState(binding = binding, visibility = true)
                    }
                    is MainFragmentUiState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                    is MainFragmentUiState.Loading -> {
                        changeViewState(binding = binding, visibility = false)
                    }
                }
            }
        }
    }

    private fun setupRecyclerViews(binding: MainFragmentBinding) {
        popularMainFragmentAdapter = MainFragmentListAdapter(this)
        binding.popularRecV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.popularRecV.adapter = popularMainFragmentAdapter

        topMainFragmentAdapter = MainFragmentListAdapter(this)
        binding.topRecV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.topRecV.adapter = topMainFragmentAdapter

        awaitMainFragmentAdapter = MainFragmentListAdapter(this)
        binding.awaitRecV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.awaitRecV.adapter = awaitMainFragmentAdapter
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MainFragmentBinding = MainFragmentBinding.inflate(inflater, container, false)

    private fun changeViewState(binding: MainFragmentBinding, visibility: Boolean) {
        binding.shimmerAwait.bindVisibility(!visibility)
        binding.shimmerTop.bindVisibility(!visibility)
        binding.shimmerPopular.bindVisibility(!visibility)
        binding.topRecV.bindVisibility(visibility)
        binding.popularRecV.bindVisibility(visibility)
        binding.awaitRecV.bindVisibility(visibility)
    }

    override fun onClickedFilm(film: Film) {
        val action = MainFragmentDirections.actionMainFragmentToFilmFragment(film)
        findNavController().navigate(action)
    }
}
