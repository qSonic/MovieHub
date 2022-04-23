package com.example.moviehub.ui.filmpage

import androidx.lifecycle.*
import com.example.moviehub.data.db.FilmDao
import com.example.moviehub.data.model.Film
import com.example.moviehub.data.repository.MovieRepository
import com.example.moviehub.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val filmDao: FilmDao,
) : ViewModel() {
    private val _id = MutableLiveData<Int>()
    val isFavourite: MutableLiveData<Boolean> = MutableLiveData()

    private val ioScope = CoroutineScope(Dispatchers.IO)

    val filmLiveData = _id.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
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
        isFavourite.postValue(filmDao.checkFavourite(film.filmId!!))
    }

    fun provideId(id: Int) {
        _id.value = id
    }
}
