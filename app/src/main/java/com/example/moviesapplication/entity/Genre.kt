package com.example.moviesapplication.entity

import com.google.gson.annotations.SerializedName


data class GenreResponse(
    @SerializedName("genres")
    val result: List<Genre>
)

data class SearchResponse(
    @SerializedName("results")
    val results: List<Genre>
)

data class Genre(
    val id: Int?,
    val name: String?
)