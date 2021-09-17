package com.example.moviesapplication.entity

import com.google.gson.annotations.SerializedName

data class ResponseItems(
//    @SerializedName("page")
//    val page: Int?,
    @SerializedName("results")
    val movieItems: List<MovieItem>?,
//    @SerializedName("total_pages")
//    val totalPages: Int?,
//    @SerializedName("total_results")
//    val totalResults: Int?
)

data class MovieItem(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
)