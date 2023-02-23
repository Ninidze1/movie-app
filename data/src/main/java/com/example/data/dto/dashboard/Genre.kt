package com.example.data.dto.dashboard

import com.google.gson.annotations.SerializedName


data class GenreResponse(
    @SerializedName("genres")
    val result: List<Genre>
)

data class Genre(
    val id: Int?,
    val name: String?
)