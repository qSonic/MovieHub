package com.example.moviehub.data.api

import com.example.moviehub.util.Constants.QUERY_AWAIT
import com.example.moviehub.util.Constants.QUERY_POPULAR // ktlint-disable no-wildcard-imports
import com.example.moviehub.util.Constants.QUERY_TOP
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieService: MovieService
) : BaseDataSource() {

    suspend fun getTopFilms() = getResult { movieService.getFilms(QUERY_TOP) }
    suspend fun getPopularFilms() = getResult { movieService.getFilms(QUERY_POPULAR) }
    suspend fun getAwaitFilms() = getResult { movieService.getFilms(QUERY_AWAIT) }
    suspend fun getFilm(id: Int) = getResult { movieService.getFilm(id) }
    suspend fun searchFilms(keyword: String, pagesCount: Int) =
        getResult { movieService.searchFilms(keyword, pagesCount) }
}
