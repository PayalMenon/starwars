package com.example.starwars.api

import com.example.starwars.models.Films
import com.example.starwars.models.People
import com.example.starwars.models.Vehicles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface  ApiService {

    @GET("people")
    suspend fun getPeopleList() : Response<People>

    @GET("people")
    suspend fun getPerson(@Query("search") queryString : String) : Response<People>

    @GET("films")
    suspend fun getFilms() : Response<Films>

    @GET("vehicles")
    suspend fun getVehicles() : Response<Vehicles>
}