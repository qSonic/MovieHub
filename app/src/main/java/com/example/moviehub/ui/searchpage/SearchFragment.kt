package com.example.moviehub.ui.searchpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviehub.adapters.FilmItemListener
import com.example.moviehub.adapters.SearchFragmentListAdapter
import com.example.moviehub.data.model.Film
import com.example.moviehub.databinding.SearchFragmentBinding
import com.example.moviehub.util.Constants.DELAY_SEARCH
import com.example.moviehub.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), LifecycleObserver, FilmItemListener {

    val viewModel: SearchViewModel by viewModels()

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchFragmentAdapter: SearchFragmentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupSearch()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.searchResponse.observe(
            viewLifecycleOwner,
            {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        searchFragmentAdapter.setItems(it.data!!.films)
                        binding.progressBar.visibility = View.GONE
                    }
                    Resource.Status.ERROR ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    Resource.Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun setupSearch() {
        var searchJob: Job? = null
        binding.searchView.addTextChangedListener { editable ->
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

    private fun setupRecyclerView() {
        searchFragmentAdapter = SearchFragmentListAdapter(this)
        binding.searchRecV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.searchRecV.adapter = searchFragmentAdapter
    }

    override fun onClickedFilm(film: Film) {
        val action = SearchFragmentDirections.actionSearchFragmentToFilmFragment(film)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
