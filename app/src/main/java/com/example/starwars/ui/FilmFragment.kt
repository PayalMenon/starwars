package com.example.starwars.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.R
import com.example.starwars.models.Character
import com.example.starwars.models.Film
import com.example.starwars.models.Vehicle
import com.example.starwars.viewmodel.SearchViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.film_fragment.view.*
import javax.inject.Inject

class FilmFragment : DaggerFragment() {

    companion object {
        private const val FILM_CONSTANT = "film"

        fun newInstance(film : Film) : FilmFragment {
            val bundle = Bundle()
            bundle.putParcelable(FILM_CONSTANT, film)
            val filmFragment = FilmFragment()
            filmFragment.arguments = bundle
            return filmFragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.film_fragment, container, false)
        searchViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(
            SearchViewModel::class.java)
        this.setHasOptionsMenu(false)

        view?.let {
            val film = arguments?.getParcelable<Film>(FILM_CONSTANT)
            film?.let {movie ->
                setupViews(view, movie)
                setupCharactersList(view, searchViewModel.getCharacterList())
                setupVehiclesList(view, searchViewModel.getVehicleList())
            }
        }

        return view
    }

    private fun setupViews(view: View, film: Film) {
        view.name.text = film.title
        view.director.text = film.director
    }

    private fun setupCharactersList(view: View, characters: List<Character>?) {
        characters?.let { list ->
            if(list.isNotEmpty()) {
                val adapter = CharacterAdapter() {character: Character ->  onCharacterClicked(character) }
                view.characters_list.adapter = adapter
                view.characters_list.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                adapter.setCharactersList(list)
                view.character_title.visibility = View.VISIBLE
                view.characters_list.visibility = View.VISIBLE
            }
        }
    }

    private fun setupVehiclesList(view:View, vehicles: List<Vehicle>?) {
        vehicles?.let { list ->
            if(list.isNotEmpty()) {
                val adapter = VehicleAdapter()
                view.vehicles_list_view.adapter = adapter
                view.vehicles_list_view.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                adapter.setVehiclesList(list)
                view.vehicle_title.visibility = View.VISIBLE
                view.vehicles_list_view.visibility = View.VISIBLE
            }
        }
    }

    fun onCharacterClicked(character: Character) {
        searchViewModel.onCharacterClicked(character)
    }
}