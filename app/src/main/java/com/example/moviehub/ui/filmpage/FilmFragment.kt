package com.example.moviehub.ui.filmpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviehub.R
import com.example.moviehub.data.model.Film
import com.example.moviehub.data.model.FilmResponse
import com.example.moviehub.databinding.FilmFragmentBinding
import com.example.moviehub.ui.core.BaseFragment
import com.example.moviehub.ui.core.observeChanges
import com.example.moviehub.util.Resource
import com.example.moviehub.util.StringUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmFragment : BaseFragment<FilmFragmentBinding>() {

    val viewModel: FilmViewModel by viewModels()

    private lateinit var filmArg: Film
    lateinit var film: Film

    override fun onBindingCreated(binding: FilmFragmentBinding, savedInstanceState: Bundle?) {
        super.onBindingCreated(binding, savedInstanceState)

        filmArg = arguments?.get("film") as Film
        viewModel.provideId(filmArg.filmId!!)

        Glide.with(binding.root)
            .load(filmArg.posterUrlPreview)
            .dontTransform()
            .placeholder(R.color.shimmer_placeholder)
            .into(binding.expandedImage)

        observeChanges(viewModel.filmLiveData) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.checkFavourite(it.data!!.data)
                    bind(data = it.data, binding = binding)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> Unit
            }
        }

        observeChanges(viewModel.isFavourite) { binding.fab.isChecked = it }

        binding.fab.setOnClickListener {
            if (binding.fab.isChecked) {
                viewModel.addToFavourites(film)
                Toast.makeText(requireContext(), getString(R.string.text_added), Toast.LENGTH_SHORT).show()
            } else {
                viewModel.removeFromFavourites(film)
                Toast.makeText(requireContext(), getString(R.string.text_removed), Toast.LENGTH_SHORT).show()
            }
        }

        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun bind(binding: FilmFragmentBinding, data: FilmResponse) {
        film = data.data
        film.rating = filmArg.rating
        binding.collapsingToolbarLayout.title = film.nameRu
        binding.country.text = StringUtil.formatList(getString(R.string.text_country, film.countries.toString()))
        binding.rating.text = film.rating.toString()

        if (binding.rating.text.contains("%") || binding.rating.text.equals("0.0")) {
            binding.rating.isVisible = false
        }

        binding.description.text = film.description
        binding.slogan.text = getString(R.string.text_slogan, film.slogan)
        if (film.slogan.isNullOrEmpty()) binding.slogan.isVisible = false
        binding.year.text = getString(R.string.text_year, film.year)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FilmFragmentBinding = FilmFragmentBinding.inflate(inflater, container, false)

}
