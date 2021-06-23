package com.example.moviehub.ui.filmpage

import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import com.example.moviehub.data.model.FilmResponse
import com.example.moviehub.data.repository.MovieRepository
import com.example.moviehub.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class FilmViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _id = MutableLiveData<Int>()
    val data: LiveData<Resource<FilmResponse>> = MutableLiveData()
    val film = _id.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(movieRepository.getFilm(id))
        }
    }

    fun provideId(id: Int) {
        _id.value = id
    }
}
