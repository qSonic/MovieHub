package com.example.moviehub.data.db

import androidx.room.*
import com.example.moviehub.data.model.Film

@Dao
interface FilmDao {
    @Query("SELECT * FROM films WHERE isFavourite = 1")
    suspend fun getFavourites(): List<Film>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(film: Film)

    @Query("SELECT EXISTS (SELECT 1 FROM films WHERE filmId = :id)")
    suspend fun checkFavourite(id: Int): Boolean

    @Delete
    suspend fun deleteFilm(film: Film)
}
