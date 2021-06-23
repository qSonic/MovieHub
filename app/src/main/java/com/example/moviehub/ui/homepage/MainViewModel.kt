package com.example.moviehub.ui.homepage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    }

    val topFilmsResponse: MutableLiveData<Resource<FilmsResponse>> = MutableLiveData()
    val popularFilmsResponse: MutableLiveData<Resource<FilmsResponse>> = MutableLiveData()
    val awaitFilmsResponse: MutableLiveData<Resource<FilmsResponse>> = MutableLiveData()

    private fun getTopFilms() = viewModelScope.launch {
        val popularResponse = movieRepository.getPopularFilms()
        val topResponse = movieRepository.getTopFilms()
        val awaitResponse = movieRepository.getAwaitFilms()

        awaitFilmsResponse.postValue(awaitResponse)
        topFilmsResponse.postValue(topResponse)
        popularFilmsResponse.postValue(popularResponse)
    }
}
