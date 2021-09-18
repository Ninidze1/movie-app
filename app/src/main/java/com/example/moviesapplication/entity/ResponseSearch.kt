package com.example.moviesapplication.entity

import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("results")
    val results: List<Search>
)

data class Search(
    val id: Int?,
    val title: String?
)