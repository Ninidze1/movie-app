package com.example.moviesapplication.entity.person

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavMovie(
    @PrimaryKey val movieId: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "vote") val vote: Double?,
    @ColumnInfo(name = "lang") val lang: String?,
    @ColumnInfo(name = "year") val year: String?,
    @ColumnInfo(name = "poster") val poster: String?,
) {
    constructor(
        title: String?,
        vote: Double?,
        lang: String?,
        year: String?,
        poster: String?
    ) : this(0, title, vote, lang, year, poster)

}
