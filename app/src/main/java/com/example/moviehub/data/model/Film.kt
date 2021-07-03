package com.example.moviehub.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "films"
)
@Parcelize
data class Film(
    @PrimaryKey(autoGenerate = true)
    val filmId: Int? = null,
    val nameRu: String,
    val nameEn: String,
    val year: String?,
    val description: String?,
    var rating: Number?,
    val filmLength: String?,
    val ratingVoteCount: Int,
    val posterUrl: String,
    val posterUrlPreview: String,
    var slogan: String?,
    val countries: List<Country>,
    var isFavourite: Int = 0

) : Parcelable
