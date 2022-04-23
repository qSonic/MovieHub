package com.example.moviehub.ui.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.model.FilmsResponse
import com.example.moviehub.data.repository.MovieRepository
import com.example.moviehub.util.Resource
import com.example.moviehub.util.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    private val topFilmsLiveData = MutableStateFlow<Resource<FilmsResponse>>(value = Resource.loading())
    private val popularFilmsLiveData = MutableStateFlow<Resource<FilmsResponse>>(value = Resource.loading())
    private val awaitFilmsLiveData = MutableStateFlow<Resource<FilmsResponse>>(value = Resource.loading())

    val uiState = combine(listOf(topFilmsLiveData, popularFilmsLiveData, awaitFilmsLiveData)) { flowList ->
        if (flowList.all { it.status == Resource.Status.SUCCESS })
            MainFragmentUiState.Success(items = flowList)
        else {
            val errorResponse = flowList.find { it.status == Resource.Status.ERROR }
            if (errorResponse != null) {
                MainFragmentUiState.Error(errorResponse.message)
            } else {
                MainFragmentUiState.Loading
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val topResponse = async { movieRepository.getTopFilms() }
            val popularResponse = async { movieRepository.getPopularFilms() }
            val awaitResponse = async { movieRepository.getAwaitFilms() }

            awaitFilmsLiveData.emit(handleResponse(awaitResponse.await()))
            popularFilmsLiveData.emit(handleResponse(popularResponse.await()))
            topFilmsLiveData.emit(handleResponse(topResponse.await()))
        }
    }
}
sealed class MainFragmentUiState {
    data class Success(val items: Array<Resource<FilmsResponse>>) : MainFragmentUiState()
    data class Error(val message: String?) : MainFragmentUiState()
    object Loading : MainFragmentUiState()
}
