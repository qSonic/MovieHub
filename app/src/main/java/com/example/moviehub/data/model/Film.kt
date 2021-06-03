package com.example.moviehub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "films"
)
data class Film(
    @PrimaryKey( autoGenerate = true)
    var id: Int? = null,
    var name: String,
    var year: Int,
    var rating :Boolean,
    var ratingVoteCount : Int,
    var posterUrl: String,
    var posterUrlPreview: String
)
