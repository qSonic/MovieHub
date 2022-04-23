package com.example.moviehub.ui.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.model.FilmsResponse
import com.example.moviehub.data.repository.MovieRepository
import com.example.moviehub.util.Resource
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

    private val topFilmsLiveData = MutableStateFlow<Resource<FilmsResponse>>(value = Resource.Loading())
    private val popularFilmsLiveData = MutableStateFlow<Resource<FilmsResponse>>(value = Resource.Loading())
    private val awaitFilmsLiveData = MutableStateFlow<Resource<FilmsResponse>>(value = Resource.Loading())

    val uiState = combine(listOf(topFilmsLiveData, popularFilmsLiveData, awaitFilmsLiveData)) { flowList ->
        if (flowList.all { it is Resource.Success })
            MainFragmentUiState.Success(items = flowList)
        else {
            val errorResponse = flowList.find { it is Resource.Error } as Resource.Error?
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

            awaitFilmsLiveData.emit(awaitResponse.await())
            popularFilmsLiveData.emit(popularResponse.await())
            topFilmsLiveData.emit(topResponse.await())
        }
    }
}
sealed class MainFragmentUiState {
    data class Success(val items: Array<Resource<FilmsResponse>>) : MainFragmentUiState()
    data class Error(val message: String?) : MainFragmentUiState()
    object Loading : MainFragmentUiState()
}
