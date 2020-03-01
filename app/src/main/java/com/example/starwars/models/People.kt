package com.example.starwars.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class People(
    val results : List<Character>
)

@Parcelize
data class Character(
    val name: String,
    val gender : String,
    @SerializedName("birth_year")
    val birthYear : String,
    val vehicles : List<String>,
    val films : List<String>,
    val species : List<String>
) : Parcelable