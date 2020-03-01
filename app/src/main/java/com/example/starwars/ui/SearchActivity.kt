package com.example.starwars.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.starwars.R
import com.example.starwars.utils.EventObserver
import com.example.starwars.viewmodel.SearchViewModel
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * TODO: I need to add a "Re-try" button for the case where the fetch fails for any reason (eg: No network) and
 * all the data needs to be re-fetched
 */
class SearchActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var searchViewModel : SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        searchViewModel.initialize()

        searchViewModel.showSearchInfoScreen.observe(this, EventObserver {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, UserFragment()).commit()
        })

        searchViewModel.showFilmFragment.observe(this, EventObserver{ film ->
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FilmFragment.newInstance(film)).commit()
        })

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, SearchFragment()).commit()
    }
}
