package com.example.moviehub.data.model

data class SearchResponse(
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int,
    var films: ArrayList<Film>
)
