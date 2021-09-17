package com.example.moviesapplication.entity

import com.google.gson.annotations.SerializedName

data class MovieItem(
    val backdrop_path: String?,
    val id: Int?,
    val name: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)