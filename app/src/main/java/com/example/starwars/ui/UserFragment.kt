package com.example.starwars.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.R
import com.example.starwars.models.Film
import com.example.starwars.viewmodel.SearchViewModel
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.user_fragment.view.*
import javax.inject.Inject

class UserFragment : DaggerFragment() {
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
        val view = inflater.inflate(R.layout.user_fragment, container, false)
        searchViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(
            SearchViewModel::class.java)
        this.setHasOptionsMenu(false)
        view?.let {
            view.name.text = searchViewModel.uiData?.displayInfo?.name
            setupFilmsList(view)
            setupVehiclesList(view)
        }

        return view
    }

    private fun setupFilmsList(view : View) {
        val adapter = FilmsAdapter {film : Film -> onFilmClicked(film)}
        view.films_list_view.adapter = adapter
        view.films_list_view.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        searchViewModel.uiData?.films.let { films ->
            films?.let {
                adapter.setFilmsList(it)
                view.films_list_view.visibility = View.VISIBLE
                view.film_title.visibility = View.VISIBLE
            }
        }

    }

    private fun setupVehiclesList(view : View) {
        val adapter = VehicleAdapter()
        view.vehicles_list_view.adapter = adapter
        view.vehicles_list_view.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        searchViewModel.uiData?.vehicles.let { vehicles ->
            if(!vehicles.isNullOrEmpty()) {
                adapter.setVehiclesList(vehicles)
                view.vehicles_list_view.visibility = View.VISIBLE
                view.vehicle_title.visibility = View.VISIBLE
            }
        }
    }

    private fun onFilmClicked(film : Film) {
        searchViewModel.onFilmClicked(film)
    }
}