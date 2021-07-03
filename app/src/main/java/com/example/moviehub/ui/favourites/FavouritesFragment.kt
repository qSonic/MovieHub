package com.example.moviehub.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviehub.adapters.FilmItemListener
import com.example.moviehub.adapters.SearchFragmentListAdapter
import com.example.moviehub.data.model.Film
import com.example.moviehub.databinding.FavouritesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment(), FilmItemListener {

    val viewModel: FavouritesViewModel by viewModels()

    private var _binding: FavouritesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var favouritesFragmentAdapter: SearchFragmentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FavouritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setItemDragListener()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getFavourites().observe(
            viewLifecycleOwner,
            { films ->
                favouritesFragmentAdapter.setItems(films)
            }
        )
    }

    private fun setupRecyclerView() {
        favouritesFragmentAdapter = SearchFragmentListAdapter(this)
        binding.favouritesRecV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.favouritesRecV.adapter = favouritesFragmentAdapter
    }

    private fun setItemDragListener() {
        val itemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    viewModel.removeFromFavourites(favouritesFragmentAdapter.getItem(position))
                    favouritesFragmentAdapter.deleteFilm(position)
                }
            }

        ItemTouchHelper(itemTouchCallback).apply {
            attachToRecyclerView(binding.favouritesRecV)
        }
    }

    override fun onClickedFilm(film: Film) {
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToFilmFragment(film)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
