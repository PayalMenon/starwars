package com.example.starwars.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Films(
    val count:Int,
    val results: List<Film>
)

@Parcelize
data class Film (
    val title: String,
    val director: String,
    @SerializedName("opening_crawl")
    val description: String,
    val producer: String,
    val url: String,
    val characters: List<String>,
    val vehicles: List<String>
) : Parcelable