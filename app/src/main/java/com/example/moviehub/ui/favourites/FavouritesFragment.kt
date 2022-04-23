package com.example.moviehub.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviehub.adapters.FilmItemListener
import com.example.moviehub.adapters.SearchFragmentListAdapter
import com.example.moviehub.data.model.Film
import com.example.moviehub.databinding.FavouritesFragmentBinding
import com.example.moviehub.ui.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FavouritesFragmentBinding>(), FilmItemListener {

    val viewModel: FavouritesViewModel by viewModels()

    private lateinit var favouritesFragmentAdapter: SearchFragmentListAdapter

    override fun onBindingCreated(binding: FavouritesFragmentBinding, savedInstanceState: Bundle?) {
        super.onBindingCreated(binding, savedInstanceState)
        setItemDragListener()

        favouritesFragmentAdapter = SearchFragmentListAdapter(this)
        binding.favouritesRecV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.favouritesRecV.adapter = favouritesFragmentAdapter

        lifecycleScope.launchWhenStarted {
            viewModel.getFavourites().collectLatest {
                favouritesFragmentAdapter.setItems(it)
            }
        }
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
            attachToRecyclerView(binding?.favouritesRecV)
        }
    }

    override fun onClickedFilm(film: Film) {
        val action = FavouritesFragmentDirections.actionFavouritesFragmentToFilmFragment(film)
        findNavController().navigate(action)
    }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FavouritesFragmentBinding = FavouritesFragmentBinding.inflate(inflater, container, false)
}
