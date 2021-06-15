package com.example.moviehub.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviehub.adapters.FilmItemListener
import com.example.moviehub.adapters.FilmsListAdapter
import com.example.moviehub.databinding.MainFragmentBinding
import com.example.moviehub.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), LifecycleObserver, FilmItemListener {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var popularFilmsAdapter: FilmsListAdapter
    private lateinit var topFilmsAdapter: FilmsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupObservers()
        setupRecyclerView()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        popularFilmsAdapter = FilmsListAdapter(this)
        binding.popularRecV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.popularRecV.adapter = popularFilmsAdapter

        topFilmsAdapter = FilmsListAdapter(this)
        binding.topRecV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.topRecV.adapter = topFilmsAdapter
    }

    private fun setupObservers() {
        viewModel.topFilmsResponse.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        topFilmsAdapter.setItems(it.data!!.films)
                    }
                    Resource.Status.ERROR ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    Resource.Status.LOADING ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    // binding.progressBar.visibility = View.VISIBLE
                }
            }
        )

        viewModel.popularFilmsResponse.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        popularFilmsAdapter.setItems(it.data!!.films)
                    }
                    Resource.Status.ERROR ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    Resource.Status.LOADING ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    // binding.progressBar.visibility = View.VISIBLE
                }
            }
        )
    }

    override fun onClickedFilm(filmId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToFilmFragment(filmId)
        findNavController().navigate(action)
    }
}
