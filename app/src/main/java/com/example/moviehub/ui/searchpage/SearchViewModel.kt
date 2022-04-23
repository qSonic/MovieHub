package com.example.moviehub.ui.searchpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.model.SearchResponse
import com.example.moviehub.data.repository.MovieRepository
import com.example.moviehub.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    val searchFlow = MutableStateFlow<Resource<SearchResponse>>(value = Resource.Loading())
    private val searchPage = 1
    fun searchFilms(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        searchFlow.emit(Resource.Loading())
        searchFlow.emit(movieRepository.searchFilms(keyword, searchPage))
    }
}
