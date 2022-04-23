package com.example.moviehub.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.model.Film
import com.example.moviehub.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    init {
        getFavourites()
    }

    fun getFavourites() = flow {
        withContext(Dispatchers.IO) {
            emit(movieRepository.getFavourites())
        }
    }

    fun removeFromFavourites(film: Film) = viewModelScope.launch {
        movieRepository.deleteFilm(film)
    }
}
