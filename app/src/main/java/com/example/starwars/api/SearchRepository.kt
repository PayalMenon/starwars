package com.example.starwars.api

import com.example.starwars.models.*
import retrofit2.Response
import javax.inject.Inject

//TODO: All the exceptions need to result in update to the UI
class SearchRepository @Inject constructor(private val apiService: ApiService) {

    // TODO: This is a temporary cache for data. Ideally this data should go in the room DB
    companion object {
        var filmList: MutableList<Film>? = null
        var vehicleList : MutableList<Vehicle>? = null
        var characterList : MutableList<Character>? = null
    }

    suspend fun fetchCharacters() {
        try {
            val response: Response<People> = apiService.getPeopleList()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    characterList = body.results.toMutableList()
                }
            }
        } catch (exception : Exception){
            // log exception
        }
    }

    suspend fun fetchFilms() {
        try {
            val response: Response<Films> = apiService.getFilms()
            if(response.isSuccessful) {
                response.body()?.let { body ->
                    filmList = body.results.toMutableList()
                }
            }
        } catch (exception: Exception) {
            // log exception
        }
    }

    suspend fun fetchVehicles() {
        try {
            val response: Response<Vehicles> = apiService.getVehicles()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    vehicleList = body.results.toMutableList()
                }
            }
        } catch (exception: Exception) {
            // Log exception
        }
    }

    suspend fun getCharacter(queryString : String) : Character? {
        val subList = characterList?.filter { it.name == queryString }
        subList?.let { list ->
            if(list.isNotEmpty() ) return list[0]
        }
        return fetchPerson(queryString)
    }

    suspend fun fetchPerson(queryString: String) : Character? {
        try {
            val response: Response<People> = apiService.getPerson(queryString)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    characterList?.add(body.results[0])
                    return body.results[0]
                }
            }
        } catch (exception : Exception) {
            // Log exception
            return null
        }
        return null
    }

    //TODO: The below methods also should fetch the list incase the item is not found in the list or
    // the list is empty, similar to the person call
    fun getFilmList() : List<Film>? {
        return filmList
    }

    fun getCharacterList() : List<Character>? {
        return characterList
    }

    fun getVehicleList() : List<Vehicle>? {
        return vehicleList
    }
}