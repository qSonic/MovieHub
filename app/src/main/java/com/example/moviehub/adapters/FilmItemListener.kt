package com.example.moviehub.adapters

import com.example.moviehub.data.model.Film

interface FilmItemListener {
    fun onClickedFilm(film: Film)
}
