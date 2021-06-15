package com.example.moviehub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "films"
)
data class Film(
    @PrimaryKey(autoGenerate = true)
    var filmId: Int? = null,
    var nameRu: String,
    var year: String,
    var rating: Number,
    var ratingVoteCount: Int,
    var posterUrl: String,
    var posterUrlPreview: String
)
