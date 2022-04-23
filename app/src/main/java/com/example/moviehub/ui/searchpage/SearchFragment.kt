package com.example.moviehub.ui.searchpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviehub.adapters.FilmItemListener
import com.example.moviehub.adapters.SearchFragmentListAdapter
import com.example.moviehub.data.model.Film
import com.example.moviehub.databinding.SearchFragmentBinding
import com.example.moviehub.ui.core.BaseFragment
import com.example.moviehub.ui.core.observeChanges
import com.example.moviehub.util.Constants.DELAY_SEARCH
import com.example.moviehub.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchFragmentBinding>(), FilmItemListener {

    val viewModel: SearchViewModel by viewModels()
    private lateinit var searchFragmentAdapter: SearchFragmentListAdapter

    override fun onBindingCreated(binding: SearchFragmentBinding, savedInstanceState: Bundle?) {
        super.onBindingCreated(binding, savedInstanceState)

        searchFragmentAdapter = SearchFragmentListAdapter(this)
        binding.searchRecV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchRecV.adapter = searchFragmentAdapter

        setupSearch()

        observeChanges(viewModel.searchFlow) {
            when (it) {
                is Resource.Success -> {
                    searchFragmentAdapter.setItems(it.data!!.films)
                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Error ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupSearch() {
        var searchJob: Job? = null
        binding?.searchView?.addTextChangedListener { editable ->
            searchJob?.cancel()
            searchJob = MainScope().launch {
                delay(DELAY_SEARCH)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchFilms(editable.toString())
                    }
                }
            }
        }
    }

    override fun onClickedFilm(film: Film) {
        val action = SearchFragmentDirections.actionSearchFragmentToFilmFragment(film)
        findNavController().navigate(action)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SearchFragmentBinding = SearchFragmentBinding.inflate(inflater, container, false)
}
