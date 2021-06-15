package com.example.moviehub.ui.homepage

import androidx.lifecycle.*
import com.example.moviehub.data.model.FilmsResponse
import com.example.moviehub.data.repository.MovieRepository
import com.example.moviehub.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    init {
        getTopFilms()
        getPopularFilms()
    }

    val topFilmsResponse: MutableLiveData<Resource<FilmsResponse>> = MutableLiveData()
    val popularFilmsResponse: MutableLiveData<Resource<FilmsResponse>> = MutableLiveData()
    val awaitFilmsResponse: MutableLiveData<Resource<FilmsResponse>> = MutableLiveData()

    private fun getTopFilms() = viewModelScope.launch {
        val response = movieRepository.getTopFilms()
        topFilmsResponse.postValue(response)
    }

    private fun getPopularFilms() = viewModelScope.launch {
        val response = movieRepository.getPopularFilms()
        popularFilmsResponse.postValue(response)
    }

    private fun getAwaitFilms() = viewModelScope.launch {
        val response = movieRepository.getAwaitFilms()
        awaitFilmsResponse.postValue(response)
    }
}
