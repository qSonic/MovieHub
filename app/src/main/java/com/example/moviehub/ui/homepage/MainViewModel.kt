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

    val topFilmsResponse: MutableLiveData<Resource<FilmsResponse>> = MutableLiveData()
    val popularFilmsResponse: MutableLiveData<Resource<FilmsResponse>> = MutableLiveData()
    val awaitFilmsResponse: MutableLiveData<Resource<FilmsResponse>> = MutableLiveData()

    init {
        getTopFilms()
    }

    private fun getTopFilms() = viewModelScope.launch {

        topFilmsResponse.postValue(Resource.loading())
        val topResponse = movieRepository.getTopFilms()

        popularFilmsResponse.postValue(Resource.loading())
        val popularResponse = movieRepository.getPopularFilms()

        awaitFilmsResponse.postValue(Resource.loading())
        val awaitResponse = movieRepository.getAwaitFilms()

        awaitFilmsResponse.postValue(handleResponse(awaitResponse))
        popularFilmsResponse.postValue(handleResponse(popularResponse))
        topFilmsResponse.postValue(handleResponse(topResponse))
    }

    private fun handleResponse(response: Resource<FilmsResponse>): Resource<FilmsResponse> {
        if (response.status == Resource.Status.SUCCESS) {
            return Resource.success(response.data!!)
        }
        return Resource.error(response.message)
    }
}
