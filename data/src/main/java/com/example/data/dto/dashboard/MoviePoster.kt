package com.example.data.dto.dashboard

import com.google.gson.annotations.SerializedName

data class MoviePoster(
    val backdropPath: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("poster_path")
    val posterPath: String?,
)