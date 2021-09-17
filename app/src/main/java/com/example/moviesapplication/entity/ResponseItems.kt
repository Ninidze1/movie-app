package com.example.moviesapplication.entity

import com.google.gson.annotations.SerializedName

data class ResponseItems(
    @SerializedName("results")
    val results: List<MovieItem>
)
