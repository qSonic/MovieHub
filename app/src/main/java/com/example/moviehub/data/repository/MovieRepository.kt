package com.example.moviehub.data.repository

import com.example.moviehub.data.api.MovieRemoteDataSource
import com.example.moviehub.data.db.FilmDao
import com.example.moviehub.data.model.Film
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: FilmDao
) {
    suspend fun getTopFilms() = remoteDataSource.getTopFilms()
    suspend fun getAwaitFilms() = remoteDataSource.getAwaitFilms()
    suspend fun getPopularFilms() = remoteDataSource.getPopularFilms()
    suspend fun getFilm(id: Int) = remoteDataSource.getFilm(id)
    suspend fun searchFilms(keyword: String, pageCount: Int) = remoteDataSource.searchFilms(keyword, pageCount)
    suspend fun deleteFilm(film: Film) = localDataSource.deleteFilm(film)
    suspend fun getFavourites() = localDataSource.getFavourites()
}
