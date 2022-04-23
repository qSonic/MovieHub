package com.example.moviehub.ui.filmpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.db.FilmDao
import com.example.moviehub.data.model.Film
import com.example.moviehub.data.repository.MovieRepository
import com.example.moviehub.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class FilmViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val filmDao: FilmDao,
) : ViewModel() {
    private val idFlow = MutableStateFlow<Int>(value = Int.MIN_VALUE)
    val isFavouriteFlow = MutableStateFlow<Boolean>(value = false)

    private val ioScope = CoroutineScope(Dispatchers.IO)

    val filmFlow = idFlow.flatMapLatest { id ->
        flow {
            emit(Resource.Loading())
            val filmResponse = movieRepository.getFilm(id)
            emit(filmResponse)
        }
    }

    fun addToFavourites(film: Film) = ioScope.launch {
        film.isFavourite = 1
        filmDao.insert(film)
    }

    fun removeFromFavourites(film: Film) = ioScope.launch {
        movieRepository.deleteFilm(film)
    }

    fun checkFavourite(film: Film) = viewModelScope.launch {
        isFavouriteFlow.emit(filmDao.checkFavourite(film.filmId!!))
    }

    fun provideId(id: Int) {
        idFlow.value = id
    }
}
