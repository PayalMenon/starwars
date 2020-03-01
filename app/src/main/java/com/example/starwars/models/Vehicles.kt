package com.example.starwars.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Vehicles(
    val results: List<Vehicle>
)

@Parcelize
data class Vehicle(
    val name: String,
    val model: String,
    val url: String
) : Parcelable