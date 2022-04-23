package com.example.moviehub.ui.searchpage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviehub.data.model.SearchResponse
import com.example.moviehub.data.repository.MovieRepository
import com.example.moviehub.util.Resource
import com.example.moviehub.util.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    val searchLiveData: MutableLiveData<Resource<SearchResponse>> = MutableLiveData()
    private val searchPage = 1
    fun searchFilms(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        searchLiveData.postValue(Resource.loading())
        val response = movieRepository.searchFilms(keyword, searchPage)
        searchLiveData.postValue(handleResponse(response))
    }
}
