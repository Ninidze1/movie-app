package com.example.moviesapplication.entity


import com.google.gson.annotations.SerializedName

data class CastItem(
    @SerializedName("cast")
    val cast: List<Cast>?
)

data class Cast(
    @SerializedName("profile_path")
    val profilePath: String?
)