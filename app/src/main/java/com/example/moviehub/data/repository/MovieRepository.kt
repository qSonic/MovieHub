package com.example.moviehub.data.repository

import com.example.moviehub.data.api.MovieRemoteDataSource
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) {
    suspend fun getTopFilms() = remoteDataSource.getTopFilms()
    suspend fun getAwaitFilms() = remoteDataSource.getAwaitFilms()
    suspend fun getPopularFilms() = remoteDataSource.getPopularFilms()
}
