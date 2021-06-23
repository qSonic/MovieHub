package com.example.moviehub.data.api

import com.example.moviehub.data.model.FilmResponse
import com.example.moviehub.data.model.FilmsResponse
import com.example.moviehub.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/top")
    suspend fun getFilms(@Query("type") type: String): Response<FilmsResponse>

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.1/films/{id}?")
    suspend fun getFilm(@Path("id") id: Int): Response<FilmResponse>
}
