package com.example.moviehub.data.model

data class Rating(
    val rating: Double,
    val ratingAwait: String,
    val ratingAwaitCount: Int,
    val ratingFilmCritics: String,
    val ratingFilmCriticsVoteCount: Int,
    val ratingImdb: Double,
    val ratingImdbVoteCount: Int,
    val ratingRfCritics: String,
    val ratingRfCriticsVoteCount: Int,
    val ratingVoteCount: Int
)
