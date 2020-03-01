package com.example.starwars.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.starwars.R
import com.example.starwars.api.SearchRepository
import com.example.starwars.models.Character
import com.example.starwars.models.Film
import com.example.starwars.models.Vehicle
import com.example.starwars.utils.Event
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* I tried to fetch the list of films/ vehicles only for the values returned for a particular character, however
* it always returns either one file/vehicle/ character or all the items. It would have been a overhead to fetch
* individual film/character/vehicle all the time, so I have added the initialize call to get the metadata at first
* */

/**
 * TODO: Right now the application only fetches the data in the begning of the app. In the case where user gets an Oops,
 * the app needs to be closed and re-started. This failure needs to handle fetching the data again on users consent
 */
class SearchViewModel @Inject constructor(application: Application,
                                          private val searchRepository: SearchRepository): AndroidViewModel(application){

    var uiData : UiData? = null

    //LiveData
    private val _showLoading = MutableLiveData<Event<Unit>>()
    val showLoading : LiveData<Event<Unit>>
        get() = _showLoading

    private val _hideLoading = MutableLiveData<Event<Unit>>()
    val hideLoading : LiveData<Event<Unit>>
        get() = _hideLoading

    private val _hideText = MutableLiveData<Event<Unit>>()
    val hideText : LiveData<Event<Unit>>
        get() = _hideText

    private val _showText = MutableLiveData<Event<Int>>()
    val showText : LiveData<Event<Int>>
        get() = _showText

    private val _showSearchInfoScreen = MutableLiveData<Event<Unit>>()
    val showSearchInfoScreen : LiveData<Event<Unit>>
        get() = _showSearchInfoScreen

    private val _showFilmFragment = MutableLiveData<Event<Film>>()
    val showFilmFragment : LiveData<Event<Film>>
        get() = _showFilmFragment

    fun initialize() {
        viewModelScope.launch {
            async {
                searchRepository.fetchFilms()
                searchRepository.fetchVehicles()
                searchRepository.fetchCharacters()
            }
        }
    }

    fun onSearchSelected(searchQuery : String) {
        _showLoading.value = Event(Unit)
        _hideText.value = Event(Unit)
        viewModelScope.launch {
            val character : Character? = searchRepository.getCharacter(searchQuery)
            character?.let { person ->
                uiData = combineResult(searchRepository.getFilmList(), searchRepository.getVehicleList(), person)
                _hideLoading.value = Event(Unit)
                _showSearchInfoScreen.value = Event(Unit)
            } ?: showError()
        }
    }

    fun showError() {
        _hideLoading.value = Event(Unit)
        _showText.value = Event(R.string.oops_message)
    }

    //TODO: Should be able to fetch the film/ vehicle that was not fetched in the intial response
    // and add them to the static list in repository
    fun combineResult(films: List<Film>?, vehicles: List<Vehicle>?, results: Character): UiData {
        val filmData = films?.let { result ->
            result.isNotEmpty().let {
                result.filter { results.films.contains(it.url) }
            }
        }
        val vehicleData = vehicles?.let { result ->
            result.isNotEmpty().let {
                result.filter { results.vehicles.contains(it.url) }
            }
        }
        return UiData(filmData, vehicleData, results)
    }

    fun onFilmClicked(film : Film) {
        _showFilmFragment.value = Event(film)
    }

    fun onCharacterClicked(character: Character){
        uiData = combineResult(searchRepository.getFilmList(), searchRepository.getVehicleList(), character)
        _showSearchInfoScreen.value = Event(Unit)
    }

    fun getCharacterList() : List<Character>? {
        return searchRepository.getCharacterList()
    }

    fun getVehicleList() : List<Vehicle>? {
        return searchRepository.getVehicleList()
    }
}

data class UiData(
    val films: List<Film>?,
    val vehicles: List<Vehicle>?,
    var displayInfo: Character
)

