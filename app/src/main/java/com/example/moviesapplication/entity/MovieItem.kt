package com.example.moviesapplication.entity

import com.google.gson.annotations.SerializedName

data class MovieItem(
    val backdrop_path: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    val id: Int?,
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("origin_country")
    val origin: List<String>?,
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)