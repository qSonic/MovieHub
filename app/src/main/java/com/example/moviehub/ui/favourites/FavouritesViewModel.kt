package com.example.moviehub.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.model.Film
import com.example.moviehub.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    init {
        getFavourites()
    }

    fun getFavourites() = liveData(Dispatchers.IO) {
        emit(movieRepository.getFavourites())
    }

    fun removeFromFavourites(film: Film) = viewModelScope.launch {
        movieRepository.deleteFilm(film)
    }
}
