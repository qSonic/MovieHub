package com.example.moviehub.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviehub.adapters.FilmItemListener
import com.example.moviehub.adapters.MainFragmentListAdapter
import com.example.moviehub.data.model.Film
import com.example.moviehub.databinding.MainFragmentBinding
import com.example.moviehub.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), LifecycleObserver, FilmItemListener {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    val viewModel: MainViewModel by viewModels()
    private lateinit var popularMainFragmentAdapter: MainFragmentListAdapter
    private lateinit var topMainFragmentAdapter: MainFragmentListAdapter
    private lateinit var awaitMainFragmentAdapter: MainFragmentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
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

    private fun setupObservers() {
        viewModel.topFilmsResponse.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        topMainFragmentAdapter.setItems(it.data!!.films)
                        binding.shimmerTop.visibility = View.GONE
                        binding.topRecV.visibility = View.VISIBLE
                    }
                    Resource.Status.ERROR ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    Resource.Status.LOADING -> Unit
                }
            }
        )

        viewModel.popularFilmsResponse.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        popularMainFragmentAdapter.setItems(it.data!!.films)
                        binding.shimmerPopular.visibility = View.GONE
                        binding.popularRecV.visibility = View.VISIBLE
                    }
                    Resource.Status.ERROR ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    Resource.Status.LOADING -> Unit
                }
            }
        )

        viewModel.awaitFilmsResponse.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        awaitMainFragmentAdapter.setItems(it.data!!.films)
                        binding.shimmerAwait.visibility = View.GONE
                        binding.awaitRecV.visibility = View.VISIBLE
                    }
                    Resource.Status.ERROR ->
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    Resource.Status.LOADING -> Unit
                }
            }
        )
    }

    override fun onClickedFilm(film: Film) {
        val action = MainFragmentDirections.actionMainFragmentToFilmFragment(film)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
