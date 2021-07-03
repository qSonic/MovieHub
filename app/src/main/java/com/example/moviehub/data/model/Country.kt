package com.example.moviehub.data.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Country(
    val country: String
) : Parcelable {
    override fun toString(): String {
        return country
    }
}
